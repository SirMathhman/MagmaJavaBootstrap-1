package com.meti.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtils {
	public static void delete(Path path) throws IOException {
		if (Files.isDirectory(path)) deleteChildren(path);
		Files.deleteIfExists(path);
	}

	private static void deleteChildren(Path path) throws IOException {
		List<Path> children;
		try (Stream<Path> stream = Files.list(path)) {
			children = stream.collect(Collectors.toList());
		}
		for (Path child : children) {
			delete(child);
		}
	}

	public static Path ensureFile(Path path) throws IOException {
		if (!Files.exists(path.getParent())) ensureDirectory(path.getParent());
		if (!Files.exists(path)) Files.createFile(path);
		return path;
	}

	public static void ensureDirectory(Path directory) throws IOException {
		if (!Files.exists(directory)) Files.createDirectories(directory);
	}
}
