package org.magma.parse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntParserTest {
	private final ObjectMapper mapper = new ObjectMapper();
	private final Parser parser = new IntParser(mapper);

	@Test
	void parse() {
		JsonNode node = parser.parse("10", null).orElseThrow();
		assertEquals("integer", node.get("type").asText());
		assertEquals(10, node.get("value").asInt());
	}
}