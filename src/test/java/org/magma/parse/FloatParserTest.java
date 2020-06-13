package org.magma.parse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FloatParserTest {
	private final ObjectMapper mapper = new ObjectMapper();
	private final Parser parser = new FloatParser(mapper);

	@Test
	void parseDouble() {
		assertTrue(parser.parse("10d", null).isEmpty());
	}

	@Test
	void parseWithIdentifier() {
		JsonNode node = parser.parse("10.0f", null).orElseThrow();
		assertEquals("float", node.get("type").asText());

		//JsonNode doesn't have a .asFloat() method. :(
		assertEquals(10.0, node.get("value").asDouble());
	}

	@Test
	void parseWithoutIdentifier() {
		JsonNode node = parser.parse("10.0", null).orElseThrow();
		assertEquals("float", node.get("type").asText());

		//JsonNode doesn't have a .asFloat() method. :(
		assertEquals(10.0, node.get("value").asDouble());
	}
}