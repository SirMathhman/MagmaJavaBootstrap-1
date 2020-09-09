package com.meti.feature;

import com.meti.compile.Compiler;
import com.meti.compile.MagmaCompiler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.meti.util.Monad.Monad;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public abstract class FeatureTest {
    private static final Path EXECUTABLE_PATH = Paths.get(".", "test.exe");
    private static final Path SOURCE_PATH = Paths.get(".", "test.c");
    private final Compiler compiler = new MagmaCompiler();

    private static String compileNative() throws InterruptedException {
        return execute("gcc", "-o", "test", "test.c").value();
    }

    private static ProcessResult execute(String... strings) throws InterruptedException {
        ProcessBuilder builder = new ProcessBuilder(strings);
        try {
            return executeExceptionally(builder);
        } catch (IOException e) {
            String message = e.getMessage();
            if (message.equals("Cannot run program \"gcc\": CreateProcess error=2, The system cannot find the file specified")) {
                fail("Cannot execute command \"gcc\". Make sure that gcc is installed before running these tests.");
            } else {
                fail(e);
            }
            return null;
        }
    }

    private static ProcessResult executeExceptionally(ProcessBuilder builder) throws IOException, InterruptedException {
        Process process;
        process = builder.start();
        int exit = process.waitFor();
        try (InputStream stream = process.getErrorStream()) {
            String errorString = read(stream);
            assertEquals("", errorString);
        }
        try (InputStream stream = process.getInputStream()) {
            return new ProcessResult(read(stream), exit);
        }
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(SOURCE_PATH);
        Files.deleteIfExists(EXECUTABLE_PATH);
    }

    @Test
    void testCompile() throws Exception {
        compileInternal();
        String output = compileNative();
        assertEquals("", output);
    }

    private void compileInternal() throws IOException {
        if (!Files.exists(SOURCE_PATH)) Files.createFile(SOURCE_PATH);
        String actual = compiler.compileImpl(source());
        Files.writeString(SOURCE_PATH, actual);
    }

    protected abstract int expectedExit();

    protected abstract String source();

    protected abstract String expectedOutput();

    private static String read(InputStream stream) throws IOException {
        ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
        stream.transferTo(errorStream);
        return errorStream.toString();
    }

    @Test
    void testContent() {
        Monad(source())
                .map(compiler::compileImpl)
                .with(compile())
                .reverse()
                .accept(Assertions::assertEquals);
    }

    protected abstract String compile();

    @Test
    void testExecutable() throws Exception {
        compileInternal();
        compileNative();
        ProcessResult result = execute("test");
        assertEquals(expectedOutput(), result.value());
        assertEquals(expectedExit(), result.exit());
    }
}
