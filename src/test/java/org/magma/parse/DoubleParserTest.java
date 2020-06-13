package org.magma.parse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DoubleParserTest {
	private final ObjectMapper mapper = new ObjectMapper();
	private final Parser parser = new DoubleParser(mapper);

	@Test
	void parseWithIdentifier() {
		JsonNode node = parser.parse("10.0d")
				.orElseThrow();
		assertEquals("double", node.get("type").asText());
		assertEquals(10, node.get("value").asDouble());
	}

	@Test
	void parseWithoutIdentifier() {
		assertTrue(parser.parse("10.0").isEmpty());
	}
}