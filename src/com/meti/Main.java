package com.meti;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Main {
	public static final Path CONFIG_PATH = Paths.get(".", ".config");
	public static final Config CONFIG = new MagmaConfig(CONFIG_PATH);
	private static final Logger LOGGER = Logger.getAnonymousLogger();

	public static void main(String[] args) {
		try {
			CONFIG.load();
		} catch (LoggedException e) {
			LOGGER.log(Level.SEVERE, "Failed to create non-existent config file.", e);
		}
	}
}
