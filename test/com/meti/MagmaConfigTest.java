package com.meti;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MagmaConfigTest {

	@Test
	void load() throws Exception {
		Path path = Paths.get(".", "temp.config");
		Files.deleteIfExists(path);
		Config config = new MagmaConfig(path);
		config.load();
		assertTrue(Files.exists(path));
		Files.deleteIfExists(path);
	}

	@Test
	void loadFailure() {
		Path path = Paths.get(".", "temp", "temp.config");
		assertThrows(LoggedException.class, () -> {
			Config config = new MagmaConfig(path);
			config.load();
		});
	}
}