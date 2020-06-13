package org.magma.parse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import org.junit.jupiter.api.Test;
import org.magma.Compiler;
import org.magma.InjectedCompiler;
import org.magma.name.IntNameResolver;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FunctionParserTest {

	@Test
	void parse() {
		ObjectMapper mapper = new ObjectMapper();
		Parser parser = new FunctionParser(mapper);
		Injector injector = Guice.createInjector(new TestModule(mapper));
		Compiler compiler = InjectedCompiler.create(injector, IntNameResolver.class);
		JsonNode node = parser.parse("(x : Int) : Int => {return x;}", compiler).orElseThrow();
		assertEquals("test", node.get("name").asText());

		JsonNode parameters = node.get("parameters");
		assertEquals(1, parameters.size());
		JsonNode item = parameters.get(0);
		assertEquals("x", item.get("name").asText());
		JsonNode instance = item.get("instance");
		assertEquals("Int", instance.get("value").asText());

		JsonNode returnNode = node.get("return");
		assertEquals("Int", returnNode.get("value").asText());

		JsonNode value = node.get("value");
		JsonNode children = value.get("children");
		assertEquals(1, children.size());
		JsonNode internalReturn = children.get(1);
		assertEquals("return", internalReturn.get("type").asText());
		JsonNode internalValue = internalReturn.get("value");
		assertEquals("variable", internalValue.get("type").asText());
		assertEquals("x", internalValue.get("value").asText());
	}

	private static final class TestModule implements Module {
		private final ObjectMapper mapper;

		private TestModule(ObjectMapper mapper) {
			this.mapper = mapper;
		}

		@Override
		public void configure(Binder binder) {
			binder.bind(ObjectMapper.class).toInstance(mapper);
			binder.bind(NameProvider.class).toInstance(() -> "test");
		}
	}
}