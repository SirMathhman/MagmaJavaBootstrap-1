package org.magma.parse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Module;
import org.junit.jupiter.api.Test;
import org.magma.Extractor;
import org.magma.InjectedExtractor;

import static org.junit.jupiter.api.Assertions.*;

class ReturnParserTest {

	@Test
	void parse() {
		ObjectMapper mapper = new ObjectMapper();
		Parser parser = new ReturnParser(mapper);
		Extractor extractor = InjectedExtractor.create(Guice.createInjector(new TestModule(mapper)), IntParser.class);
		JsonNode node = parser.parse("return 10", extractor).orElseThrow();
		assertEquals("return", node.get("type").asText());

		JsonNode value = node.get("value");
		assertEquals("integer", value.get("type").asText());
		assertEquals(10, value.get("value").asInt());
	}

	private static final class TestModule implements Module {
		private final ObjectMapper mapper;

		private TestModule(ObjectMapper mapper) {
			this.mapper = mapper;
		}

		@Override
		public void configure(Binder binder) {
			binder.bind(ObjectMapper.class).toInstance(mapper);
		}
	}
}