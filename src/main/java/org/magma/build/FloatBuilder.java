package org.magma.build;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Optional;

public class FloatBuilder implements Builder {
	@Override
	public Optional<String> build(ObjectNode node, Builder parent) {
		if (Builder.is(node, "float")) {
			return Optional.of(node.get("value").asDouble() + "f");
		}
		return Optional.empty();
	}
}
