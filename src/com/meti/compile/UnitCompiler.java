package com.meti.compile;

import com.meti.MagmaException;
import com.meti.compile.name.NameResolver;
import com.meti.compile.node.Node;
import com.meti.compile.parse.Parser;
import com.meti.compile.type.Type;
import com.meti.compile.value.ValueResolver;

import java.util.Collection;
import java.util.Collections;

public class UnitCompiler implements Compiler {
	private final Collection<NameResolver> nameResolvers;
	private final Collection<Parser> parsers;
	private final Collection<ValueResolver> valueResolvers;

	public UnitCompiler(Collection<Parser> parsers, Collection<NameResolver> nameResolvers,
	                    Collection<ValueResolver> valueResolvers) {
		this.parsers = Collections.unmodifiableCollection(parsers);
		this.nameResolvers = Collections.unmodifiableCollection(nameResolvers);
		this.valueResolvers = Collections.unmodifiableCollection(valueResolvers);
	}

	@Override
	public Node parse(String content) {
		return parsers.stream()
				.filter(parser -> parser.canParse(content))
				.map(parser -> parser.parse(content, this))
				.findFirst()
				.orElseThrow(() -> new MagmaException("Unable to parse " + content));
	}

	@Override
	public Type resolveName(String name) {
		return nameResolvers.stream()
				.filter(nameResolver -> nameResolver.canResolve(name))
				.map(nameResolver -> nameResolver.resolveName(name, this))
				.findFirst()
				.orElseThrow(() -> new MagmaException("Unable to resolve name of " + name));
	}

	@Override
	public Type resolveValue(String value) {
		return valueResolvers.stream()
				.filter(valueResolver -> valueResolver.canResolveValue(value))
				.map(valueResolver -> valueResolver.resolveValue(value, this))
				.findFirst()
				.orElseThrow(() -> new MagmaException("Unable to resolve name of " + value));
	}
}