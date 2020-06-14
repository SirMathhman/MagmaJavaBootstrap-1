package org.magma.name;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import org.magma.Extractor;

import java.util.Optional;

public class VoidNameResolver extends AbstractNameResolver {
	@Inject
	public VoidNameResolver(ObjectMapper mapper) {
		super(mapper);
	}

	@Override
	public Optional<JsonNode> resolveName(String name, Extractor extractor) {
		return Optional.of(name)
				.filter("Void"::equals)
				.map(this::wrapInNode);
	}
}
