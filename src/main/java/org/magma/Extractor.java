package org.magma;

import com.fasterxml.jackson.databind.JsonNode;

public interface Extractor {
	boolean isInstance(JsonNode parent, JsonNode child);

	JsonNode parse(String content);

	JsonNode resolveName(String name);

	JsonNode resolveValue(String value);
}
