package org.magma.parse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public abstract class JSONUnit {
	private final ObjectMapper mapper;

	protected JSONUnit(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	protected ArrayNode createArray() {
		return mapper.createArrayNode();
	}

	protected ObjectNode createObject() {
		return mapper.createObjectNode();
	}
}
