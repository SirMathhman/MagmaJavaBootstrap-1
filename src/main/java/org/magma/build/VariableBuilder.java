package org.magma.build;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Optional;

public class VariableBuilder implements Builder {
	@Override
	public Optional<String> build(JsonNode node, Builder parent) {
		if(Builder.is(node, "variable")) {
			return Optional.ofNullable(node.get("value").asText());
		}
		return Optional.empty();
	}
}
