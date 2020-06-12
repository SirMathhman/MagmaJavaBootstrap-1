package org.magma;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IntBuilderTest {
	private final Builder builder = new IntBuilder();

	@Test
	void test() {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode()
				.put("type", "integer")
				.put("value", 10);
		Optional<String> result = builder.build(node);
		assertTrue(result.isPresent());
		assertEquals("10", result.get());
	}
}
