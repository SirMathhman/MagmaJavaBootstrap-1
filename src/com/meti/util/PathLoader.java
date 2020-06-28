package com.meti.util;

import com.meti.MagmaException;
import com.meti.load.Loader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;

public class PathLoader implements Loader {
	private final Map<String, Path> sources;

	public PathLoader(String name, Path path) {
		this(Collections.singletonMap(name, path));
	}

	public PathLoader(Map<String, Path> sources) {
		this.sources = Collections.unmodifiableMap(sources);
	}

	@Override
	public String load(String name) {
		if (sources.containsKey(name)) {
			return loadValid(name);
		} else {
			throw new MagmaException(name + " was not found in " + sources);
		}
	}

	private String loadValid(String name) {
		Path path = sources.get(name);
		try {
			return Files.readString(path);
		} catch (IOException e) {
			throw new MagmaException(String.format("Failed to read main path %s", path.toAbsolutePath()), e);
		}
	}
}
