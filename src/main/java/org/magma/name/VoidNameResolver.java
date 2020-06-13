package org.magma.name;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.magma.Compiler;

import java.util.Optional;

public class VoidNameResolver extends AbstractNameResolver {
	public VoidNameResolver(ObjectMapper mapper) {
		super(mapper);
	}

	@Override
	public Optional<JsonNode> resolveName(String name, Compiler compiler) {
		return Optional.of(name)
				.filter("Void"::equals)
				.map(this::wrapInNode);
	}
}
