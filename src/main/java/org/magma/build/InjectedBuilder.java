package org.magma.build;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Injector;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

public class InjectedBuilder implements Builder {
	private final Collection<Builder> children;

	public InjectedBuilder(Collection<Builder> children) {
		this.children = Collections.unmodifiableCollection(children);
	}

	public static Builder create(Injector injector, Class<? extends Builder>... builders) {
		return new InjectedBuilder(Arrays.stream(builders)
				.map(injector::getInstance)
				.collect(Collectors.toList()));
	}

	@Override
	public Optional<String> build(JsonNode node, Builder parent) {
		return children.stream()
				.map(child -> child.build(node, this))
				.flatMap(Optional::stream)
				.findFirst();
	}
}
