package org.magma;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.magma.exception.ParseException;
import org.magma.name.NameResolver;
import org.magma.parse.Parser;
import org.magma.value.ValueResolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class InjectedCompiler implements Compiler {
	private final Collection<NameResolver> nameResolvers = new ArrayList<>();
	private final Collection<Parser> parsers = new ArrayList<>();
	private final Collection<ValueResolver> valueResolvers = new ArrayList<>();

	public InjectedCompiler(Iterable<Object> instances) {
		for (Object instance : instances) {
			if (instance instanceof Parser) parsers.add((Parser) instance);
			if (instance instanceof NameResolver) nameResolvers.add((NameResolver) instance);
			if (instance instanceof ValueResolver) valueResolvers.add((ValueResolver) instance);
		}
	}

	public static Compiler create(Class<?>... classes) {
		return create(Guice.createInjector(), classes);
	}

	public static Compiler create(Injector injector, Class<?>... classes) {
		return new InjectedCompiler(Arrays.stream(classes)
				.map(injector::getInstance)
				.collect(Collectors.toList()));
	}

	@Override
	public boolean isInstance(JsonNode parent, JsonNode child) {
		return false;
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
				.orElseThrow(() -> new ParseException("Failed to resolve name of: " + name));
	}

	@Override
	public JsonNode resolveValue(String value) {
		return valueResolvers.stream()
				.map(resolver -> resolver.resolveValue(value, this))
				.flatMap(Optional::stream)
				.findFirst()
				.orElseThrow(() -> new ParseException("Failed to resolve value of: " + value));
	}
}
