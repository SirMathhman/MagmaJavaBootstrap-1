package org.magma.parse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import org.magma.Extractor;
import org.magma.JSONUnit;

import java.util.Optional;

public class ReturnParser extends JSONUnit implements Parser {
	@Inject
	public ReturnParser(ObjectMapper mapper) {
		super(mapper);
	}

	@Override
	public Optional<JsonNode> parse(String content, Extractor extractor) {
		if (content.startsWith("return ")) {
			String value = content.substring(7).trim();
			JsonNode node = extractor.parse(value);
			return Optional.of(createObject()
					.put("type", "return")
					.set("value", node));
		}
		return Optional.empty();
	}
}
