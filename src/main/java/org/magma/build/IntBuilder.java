package org.magma.build;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Optional;

import static org.magma.build.Builder.is;

public class IntBuilder implements Builder {
	@Override
	public Optional<String> build(JsonNode node, Builder parent) {
		return Optional.of(node)
				.filter(n -> is(n, "integer"))
				.map(n -> n.get("value"))
				.map(JsonNode::asInt)
				.map(String::valueOf);
	}
}