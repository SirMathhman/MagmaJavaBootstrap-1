package com.meti;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class MagmaConfig implements Config {
	private final Path config;
	private final Properties properties;

	public MagmaConfig(Path config) {
		this(config, new Properties());
	}

	public MagmaConfig(Path config, Properties properties) {
		this.config = config;
		this.properties = new Properties(properties);
	}

	@Override
	public void load() throws LoggedException {
		if (!Files.exists(config)) {
			try {
				Files.createFile(config);
			} catch (IOException e) {
				throw new LoggedException("Failed to create non-existent configuration file.", e);
			}
		}

		try (InputStream stream = Files.newInputStream(config)) {
			properties.load(stream);
		} catch (IOException e) {
			throw new LoggedException("Failed to load properties at: %s".formatted(config), e);
		}
	}

	@Override
	public void store() throws LoggedException {
		try (OutputStream stream = Files.newOutputStream(config)) {
			properties.store(stream, "");
		} catch (IOException e) {
			throw new LoggedException("Failed to store properties at: %s".formatted(config), e);
		}
	}
}