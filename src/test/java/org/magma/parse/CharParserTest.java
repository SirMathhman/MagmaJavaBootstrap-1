package org.magma.parse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharParserTest {
	private final ObjectMapper mapper = new ObjectMapper();
	private final Parser parser = new CharParser(mapper);

	@Test
	void parse() {
		JsonNode result = parser.parse("'x'").orElseThrow();
		assertEquals("character", result.get("type").asText());

		/*
		Here result.get("value").asInt() would return 120,
		but assertEquals should be taking in characters.
		We need to cast this integer into a character via ASCII.
		 */
		assertEquals('x', (char) result.get("value").asInt());
	}

	@Test
	void parseInvalidFormat(){
		assertTrue(parser.parse("x").isEmpty());
	}

	@Test
	void parseInvalidLength(){
		assertTrue(parser.parse("'length'").isEmpty());
	}
}