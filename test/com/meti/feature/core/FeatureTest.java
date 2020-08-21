package com.meti.feature.core;

import com.meti.compile.Compiler;
import com.meti.compile.RootCompiler;
import com.meti.compile.parse.MagmaParseRule;
import com.meti.compile.resolve.primitive.IntNameRule;
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

	protected abstract String output();

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
		Compiler compiler = new RootCompiler(new MagmaParseRule(), new IntNameRule());
		String actual = compiler.parse(source()).render();
		Files.writeString(SOURCE_PATH, actual);
	}

	private static String compileNative() throws IOException, InterruptedException {
		return execute("gcc", "-o", "test", "test.c");
	}

	protected abstract String source();

	private static String execute(String... strings) throws IOException, InterruptedException {
		Process process = new ProcessBuilder(strings).start();
		process.waitFor();
		try (InputStream stream = process.getErrorStream()) {
			String errorString = read(stream);
			assertEquals("", errorString);
		}
		try (InputStream stream = process.getInputStream()) {
			return read(stream);
		}
	}

	private static String read(InputStream stream) throws IOException {
		ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
		stream.transferTo(errorStream);
		return errorStream.toString();
	}

	@Test
	void testContent() {
		Compiler compiler = new RootCompiler(new MagmaParseRule(), new IntNameRule());
		String actual = compiler.parse(source()).render();
		assertEquals(compiled(), actual);
	}

	protected abstract String compiled();

	@Test
	void testExecutable() throws Exception {
		compileInternal();
		compileNative();
		String output = execute("test");
		assertEquals(output(), output);
	}
}
