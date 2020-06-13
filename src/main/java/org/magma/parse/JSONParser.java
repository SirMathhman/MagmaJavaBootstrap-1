package org.magma.parse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Offers convenience methods for JSON creation in {@link Parser}-related contexts.
 */
public abstract class JSONParser implements Parser {
	private final ObjectMapper mapper;

	protected JSONParser(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	protected ObjectNode createObject() {
		return mapper.createObjectNode();
	}
}
