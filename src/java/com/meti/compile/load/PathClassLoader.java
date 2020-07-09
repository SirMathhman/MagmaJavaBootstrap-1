package com.meti.compile.load;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class PathClassLoader implements ClassLoader {
	private final Path path;

	public PathClassLoader(Path path) {
		this.path = path;
	}

	@Override
	public String load(String... className) throws IOException {
		Path classPath = Arrays.stream(className)
				.limit(className.length - 1)
				.reduce(path, Path::resolve, (path, path2) -> path2)
				.resolve(className[className.length - 1] + ".magma");
		if (!Files.exists(classPath)) {
			throw new LoadException(classPath.toAbsolutePath() + " doesn't exist.");
		}
		return Files.readString(classPath);
	}
}
