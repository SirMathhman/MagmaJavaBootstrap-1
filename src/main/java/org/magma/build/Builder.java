package org.magma.build;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Optional;

public interface Builder {
	static boolean is(ObjectNode node, String type) {
		JsonNode typeNode = node.get("type");
		return type.equals(typeNode.asText());
	}

	default Optional<String> build(ObjectNode node) {
		return build(node, null);
	}

	Optional<String> build(ObjectNode node, Builder parent);
}
