package com.meti.util;

import com.meti.MagmaException;
import com.meti.load.Loader;
import com.meti.load.LoaderFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathLoaderFactory implements LoaderFactory {
	private final Path source;

	public PathLoaderFactory(Path source) {
		this.source = source;
	}

	@Override
	public Loader create() {
		Map<String, Path> sources = buildMap();
		return new PathLoader(sources);
	}

	private Map<String, Path> buildMap() {
		return Files.isDirectory(source) ? buildMapAsDirectory() : buildMapAsFile();
	}

	private Map<String, Path> buildMapAsDirectory() {
		List<Path> collect = collectChildren();
		return collect.stream()
				.collect(Collectors.toMap(PathLoaderFactory::getLastName, Function.identity()));
	}

	private Map<String, Path> buildMapAsFile() {
		return Collections.singletonMap(getLastName(source), source);
	}

	private List<Path> collectChildren() {
		List<Path> collect;
		try (Stream<Path> stream = Files.walk(source)) {
			collect = stream.filter(path -> path.toString().endsWith(".magma"))
					.collect(Collectors.toList());
		} catch (IOException e) {
			throw new MagmaException("Failed to walk file tree.", e);
		}
		return collect;
	}

	private static String getLastName(Path path) {
		int count = path.getNameCount();
		Path lastPath = path.getName(count - 1);
		String withExtension = lastPath.toString();
		int extensionStart = withExtension.indexOf('.');
		return withExtension.substring(0, extensionStart);
	}
}
