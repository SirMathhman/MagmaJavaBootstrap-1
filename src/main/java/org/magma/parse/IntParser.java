package org.magma.parse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import java.util.Optional;

public class IntParser implements Parser {
	private final ObjectMapper mapper;

	@Inject
	public IntParser(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public Optional<JsonNode> parse(String content) {
		try {
			int result = Integer.parseInt(content);
			return Optional.of(mapper.createObjectNode()
					.put("type", "integer")
					.put("value", result));
		} catch (NumberFormatException e) {
			return Optional.empty();
		}
	}
}
