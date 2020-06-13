package org.magma.parse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

/**
 * Attempts to parse characters in the format of 'x', 'y', 'z',
 * where each value is surrounded by single-quotation marks.
 * The content itself must have a length of three.
 */
public class CharParser extends JSONParser {
	protected CharParser(ObjectMapper mapper) {
		super(mapper);
	}

	@Override
	public Optional<JsonNode> parse(String content) {
		if (content.startsWith("'") && content.endsWith("'") && 3 == content.length()) {
			char c = content.charAt(1);
			return Optional.of(createObject()
					.put("type", "character")
					.put("value", c));
		} else {
			return Optional.empty();
		}
	}
}
