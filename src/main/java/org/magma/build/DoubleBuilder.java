package org.magma.build;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Optional;

public class DoubleBuilder implements Builder {
	@Override
	public Optional<String> build(ObjectNode node, Builder parent) {
		if (Builder.is(node, "double")) {
			return Optional.of(node.get("value").asDouble() + "d");
		}
		return Optional.empty();
	}
}
