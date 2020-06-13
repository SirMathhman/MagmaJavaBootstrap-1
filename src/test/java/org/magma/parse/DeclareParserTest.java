package org.magma.parse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Module;
import org.junit.jupiter.api.Test;
import org.magma.Compiler;
import org.magma.InjectedCompiler;
import org.magma.name.IntNameResolver;
import org.magma.value.IntValueResolver;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeclareParserTest {
	private final ObjectMapper mapper = new ObjectMapper();
	private final Compiler compiler = InjectedCompiler.create(Guice.createInjector(new TestModule(mapper)),
			IntParser.class,
			IntNameResolver.class,
			IntValueResolver.class);
	private final Parser parser = new DeclareParser(mapper);

	/*
	val x : Int = 10;
	val x = 10;
	val x : Int;

	 */

	@Test
	void parse() {
		JsonNode node = parser.parse("val x : Int = 10", compiler).orElseThrow();
		assertEquals("declaration", node.get("type").asText());

		JsonNode flags = node.get("flags");
		assertEquals(1, flags.size());
		assertEquals("val", flags.get(0).asText());
		assertEquals("x", node.get("name").asText());

		JsonNode instance = node.get("instance");
		assertEquals("Int", instance.get("name").asText());

		JsonNode initial = node.get("initial");
		assertEquals("integer", initial.get("type").asText());
		assertEquals(10, initial.get("value").asInt());
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