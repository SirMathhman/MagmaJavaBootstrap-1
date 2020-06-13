package org.magma.name;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.magma.Extractor;

import java.util.Optional;

public class VoidNameResolver extends AbstractNameResolver {
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
