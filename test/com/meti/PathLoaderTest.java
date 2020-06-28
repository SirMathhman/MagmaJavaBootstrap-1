package com.meti;

import com.meti.load.Loader;
import com.meti.util.PathLoader;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PathLoaderTest {

	public static final String CONTENT = "test";
	public static final Path PATH = Paths.get(".", "main");

	@BeforeAll
	static void setUp() throws IOException {
		if (!Files.exists(PATH)) Files.createFile(PATH);
		Files.writeString(PATH, CONTENT);
	}

	@AfterAll
	static void tearDown() throws IOException {
		Files.deleteIfExists(PATH);
	}

	@Test
	void load() {
		Loader loader = new PathLoader("main", PATH);
		String result = loader.load("main");
		assertEquals(CONTENT, result);
	}
}