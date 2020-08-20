package com.meti;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

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

	@Test
	void store() throws Exception {
		Path path = Paths.get(".", "temp.config");
		Files.deleteIfExists(path);

		Properties properties = new Properties();
		properties.setProperty("testKey", "testValue");
		Config config = new MagmaConfig(path, properties);
		config.store();

		List<String> lines = Files.readAllLines(path);
		String joinedLines = String.join("\n", lines);
		assertFalse(joinedLines.isBlank());

		Files.deleteIfExists(path);
	}
}