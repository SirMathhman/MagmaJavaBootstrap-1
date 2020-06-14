package org.magma.parse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.inject.Inject;
import org.magma.Extractor;
import org.magma.JSONUnit;

import java.util.Arrays;
import java.util.Optional;

public class BlockParser extends JSONUnit implements Parser {
	@Inject
	public BlockParser(ObjectMapper mapper) {
		super(mapper);
	}

	@Override
	public Optional<JsonNode> parse(String content, Extractor extractor) {
		if (content.startsWith("{") && content.endsWith("}")) {
			String children = content.substring(1, content.length() - 1);
			ArrayNode items = createArray();
			Arrays.stream(children.split(";"))
					.filter(s -> !s.isBlank())
					.map(String::trim)
					.map(extractor::parse)
					.forEach(items::add);
			return Optional.of(createObject()
					.put("type", "block")
					.set("children", items));
		}
		return Optional.empty();
	}
}
