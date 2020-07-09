package com.meti.compile;

import com.meti.compile.load.ClassLoader;
import com.meti.compile.load.PathClassLoader;
import com.meti.util.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PathClassLoaderTest {
	private static final Path DIRECTORY = Paths.get(".", "tests");

	@BeforeAll
	static void setUp() throws IOException {
		FileUtils.ensureDirectory(DIRECTORY);
	}

	@AfterAll
	static void tearDown() throws IOException {
		FileUtils.delete(DIRECTORY);
	}

	@Test
	void loadPackaged() throws IOException {
		Path path = FileUtils.ensureFile(DIRECTORY.resolve("com")
				.resolve("meti")
				.resolve("Main.magma"));
		Files.writeString(path, "test");
		ClassLoader loader = new PathClassLoader(DIRECTORY);
		String main = loader.load("com", "meti", "Main");
		assertEquals("test", main);
	}

	@Test
	void loadSingleton() throws IOException {
		Path path = FileUtils.ensureFile(DIRECTORY.resolve("Main.magma"));
		Files.writeString(path, "test");
		ClassLoader loader = new PathClassLoader(DIRECTORY);
		String main = loader.load("Main");
		assertEquals("test", main);
	}
}