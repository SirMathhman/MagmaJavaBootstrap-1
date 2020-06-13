package org.magma.name;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VoidNameResolverTest {

	@Test
	void resolveName() {
		NameResolver resolver = new VoidNameResolver(new ObjectMapper());
		JsonNode type = resolver.resolveName("Void", null).orElseThrow();
		assertEquals("Void", type.get("name").asText());
	}
}