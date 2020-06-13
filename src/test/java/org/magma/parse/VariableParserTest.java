package org.magma.parse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.magma.util.TreeScope;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VariableParserTest {

	@Test
	void parse() {
		TreeScope scope = new TreeScope();
		scope.define("x", null);
		Parser parser = new VariableParser(new ObjectMapper(), scope);
		JsonNode node = parser.parse("x", null).orElseThrow();
		assertEquals("variable", node.get("type").asText());
		assertEquals("x", node.get("value").asText());
	}
}