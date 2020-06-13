package org.magma.parse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.magma.Compiler;

import java.util.Optional;

public class ReturnParser extends JSONUnit implements Parser {
	protected ReturnParser(ObjectMapper mapper) {
		super(mapper);
	}

	@Override
	public Optional<JsonNode> parse(String content, Compiler compiler) {
		if (content.startsWith("return ")) {
			String value = content.substring(7).trim();
			JsonNode node = compiler.parse(value);
			return Optional.of(createObject()
					.put("type", "return")
					.set("value", node));
		}
		return Optional.empty();
	}
}
