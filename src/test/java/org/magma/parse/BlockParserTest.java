package org.magma.parse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.magma.Compiler;
import org.magma.InjectedCompiler;

import static org.junit.jupiter.api.Assertions.*;

class BlockParserTest {

	@Test
	void parse() {
		ObjectMapper mapper = new ObjectMapper();
		Injector injector = Guice.createInjector(new TestModule(mapper));
		Compiler compiler = InjectedCompiler.create(injector, IntParser.class);
		Parser parser = new BlockParser(mapper);
		JsonNode result = parser.parse("{10;20;}", compiler).orElseThrow();

		JsonNode children = result.get("children");
		assertEquals(2, children.size());

		JsonNode first = children.get(0);
		assertEquals("integer", first.get("type").asText());
		assertEquals(10, first.get("value").asInt());

		JsonNode second = children.get(1);
		assertEquals("integer", second.get("type").asText());
		assertEquals(20, second.get("value").asInt());
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

