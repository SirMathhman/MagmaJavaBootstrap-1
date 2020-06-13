package org.magma.build;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Optional;

public interface Builder {
	static boolean is(ObjectNode node, String type) {
		JsonNode typeNode = node.get("type");
		return type.equals(typeNode.asText());
	}

	Optional<String> build(ObjectNode node);
}
