package org.magma;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Optional;

import static org.magma.Builder.is;

public class IntBuilder implements Builder {
	@Override
	public Optional<String> build(ObjectNode node) {
		return Optional.of(node)
				.filter(n -> is(n, "integer"))
				.map(n -> n.get("value"))
				.map(JsonNode::asInt)
				.map(String::valueOf);
	}
}