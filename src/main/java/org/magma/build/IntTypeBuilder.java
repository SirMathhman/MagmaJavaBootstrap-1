package org.magma.build;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Optional;

public class IntTypeBuilder implements Builder {
	@Override
	public Optional<String> build(JsonNode node, Builder parent) {
		JsonNode name = node.get("name");
		if(null != name && "Int".equals(name.asText())) {
			return Optional.of("int %s");
		}
		return Optional.empty();
	}
}
