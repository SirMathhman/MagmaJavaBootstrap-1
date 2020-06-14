package org.magma.parse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import org.junit.jupiter.api.Test;
import org.magma.Extractor;
import org.magma.InjectedExtractor;
import org.magma.name.IntNameResolver;
import org.magma.util.Scope;
import org.magma.util.TreeScope;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FunctionParserTest {

	@Test
	void parse() {
		ObjectMapper mapper = new ObjectMapper();
		Scope scope = new TreeScope();
		Parser parser = new FunctionParser(mapper, () -> "test", scope);
		Injector injector = Guice.createInjector(new TestModule(mapper, scope));
		Extractor extractor = InjectedExtractor.create(injector,
				IntNameResolver.class, BlockParser.class,
				ReturnParser.class, VariableParser.class);
		JsonNode node = parser.parse("(x : Int) : Int => {return x;}", extractor).orElseThrow();
		assertEquals("function", node.get("type").asText());
		assertEquals("test", node.get("name").asText());

		JsonNode parameters = node.get("parameters");
		assertEquals(1, parameters.size());
		JsonNode item = parameters.get(0);
		assertEquals("x", item.get("name").asText());
		JsonNode instance = item.get("instance");
		assertEquals("Int", instance.get("name").asText());

		JsonNode returnNode = node.get("return");
		assertEquals("Int", returnNode.get("name").asText());

		JsonNode value = node.get("content");
		JsonNode children = value.get("children");
		assertEquals(1, children.size());
		JsonNode internalReturn = children.get(0);
		assertEquals("return", internalReturn.get("type").asText());
		JsonNode internalValue = internalReturn.get("value");
		assertEquals("variable", internalValue.get("type").asText());
		assertEquals("x", internalValue.get("value").asText());
	}

	private static final class TestModule implements Module {
		private final ObjectMapper mapper;
		private final Scope scope;

		private TestModule(ObjectMapper mapper, Scope scope) {
			this.mapper = mapper;
			this.scope = scope;
		}

		@Override
		public void configure(Binder binder) {
			binder.bind(ObjectMapper.class).toInstance(mapper);
			binder.bind(Scope.class).toInstance(scope);
		}
	}
}