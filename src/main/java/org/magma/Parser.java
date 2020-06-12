package org.magma;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Optional;

public interface Parser {
	Optional<JsonNode> parse(String content);
}
