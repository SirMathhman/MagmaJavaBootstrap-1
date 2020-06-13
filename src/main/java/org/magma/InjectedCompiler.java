package org.magma;

import com.fasterxml.jackson.databind.JsonNode;
import org.magma.name.NameResolver;
import org.magma.parse.Parser;
import org.magma.value.ValueResolver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class InjectedCompiler implements Compiler {
	private final Collection<NameResolver> nameResolvers = new ArrayList<>();
	private final Collection<Parser> parsers = new ArrayList<>();
	private final Collection<ValueResolver> valueResolvers = new ArrayList<>();

	public InjectedCompiler(Object... instances) {
		for (Object instance : instances) {
			if (instance instanceof Parser) parsers.add((Parser) instance);
			if (instance instanceof NameResolver) nameResolvers.add((NameResolver) instance);
			if (instance instanceof ValueResolver) valueResolvers.add((ValueResolver) instance);
		}
	}

	@Override
	public JsonNode parse(String content) {
		return parsers.stream()
				.map(parser -> parser.parse(content, this))
				.flatMap(Optional::stream)
				.findFirst()
				.orElseThrow();
	}

	@Override
	public JsonNode resolveName(String name) {
		return nameResolvers.stream()
				.map(resolver -> resolver.resolveName(name, this))
				.flatMap(Optional::stream)
				.findFirst()
				.orElseThrow();
	}

	@Override
	public JsonNode resolveValue(String value) {
		return valueResolvers.stream()
				.map(resolver -> resolver.resolveValue(value, this))
				.flatMap(Optional::stream)
				.findFirst()
				.orElseThrow();
	}
}
