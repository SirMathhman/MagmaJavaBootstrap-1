package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MagmaConfig implements Config {
	private final Path config;

	public MagmaConfig(Path config) {
		this.config = config;
	}

	@Override
	public void init() throws LoggedException {
		if (!Files.exists(config)) {
			try {
				Files.createFile(config);
			} catch (IOException e) {
				throw new LoggedException("Failed to create non-existent configuration file.", e);
			}
		}
	}
}