package com.meti.feature;

import com.meti.compile.Lexer;
import com.meti.compile.RootLexer;
import com.meti.compile.parse.LexRule;
import com.meti.compile.parse.MagmaLexRule;
import com.meti.compile.resolve.MagmaResolveRule;
import com.meti.compile.resolve.ResolveRule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class FeatureTest {
	private static final Path EXECUTABLE_PATH = Paths.get(".", "test.exe");
	private static final Path SOURCE_PATH = Paths.get(".", "test.c");

	private static String compileNative() throws IOException, InterruptedException {
		return execute("gcc", "-o", "test", "test.c").value();
	}

	private static ProcessResult execute(String... strings) throws IOException, InterruptedException {
		Process process = new ProcessBuilder(strings).start();
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
		Lexer lexer = createCompiler();
		String actual = lexer.parse(source()).render();
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

	private static Lexer createCompiler() {
		LexRule rootParserRule = new MagmaLexRule();
		ResolveRule rootResolveRule = new MagmaResolveRule();
		return new RootLexer(rootParserRule, rootResolveRule);
	}

	@Test
	void testContent() {
		Lexer lexer = createCompiler();
		String actual = lexer.parse(source()).render();
		assertEquals(compile(), actual);
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
