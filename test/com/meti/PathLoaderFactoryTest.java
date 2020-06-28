package com.meti;

import com.meti.load.Loader;
import com.meti.load.LoaderFactory;
import com.meti.util.PathLoaderFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PathLoaderFactoryTest {
	public static final String CONTENT = "test";
	private static final Path PARENT = Paths.get(".", "parent");
	private static final Path CHILD = PARENT.resolve("main.magma");

	@BeforeAll
	static void setUp() throws IOException {
		if (!Files.exists(PARENT)) Files.createDirectories(PARENT);
		if (!Files.exists(CHILD)) Files.createFile(CHILD);
		Files.writeString(CHILD, CONTENT);
	}

	@AfterAll
	static void tearDown() throws IOException {
		Files.deleteIfExists(CHILD);
		Files.deleteIfExists(PARENT);
	}

	@Test
	void create() {
		LoaderFactory factory = new PathLoaderFactory(PARENT);
		Loader loader = factory.create();
		String content = loader.load("main");
		assertEquals(CONTENT, content);
	}
}