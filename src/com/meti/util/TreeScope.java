package com.meti.util;

import com.meti.MagmaException;
import com.meti.compile.type.Type;

import java.util.*;

public class TreeScope implements Scope {
	private final Map<String, Type> definitions = new HashMap<>();
	private final Scope parent;
	private final Collection<Scope> children = new ArrayList<>();

	public TreeScope() {
		this(null);
	}

	public TreeScope(Scope parent) {
		this.parent = parent;
	}

	@Override
	public void define(String name, Type type) {
		if (definitions.containsKey(name)) {
			throw new MagmaException(name + " is already defined.");
		} else {
			definitions.put(name, type);
		}
	}

	@Override
	public Scope enter() {
		TreeScope scope = new TreeScope(this);
		children.add(scope);
		return scope;
	}

	@Override
	public Optional<Scope> exit() {
		return Optional.ofNullable(parent);
	}

	@Override
	public Optional<Type> getDefinition(String name) {
		return Optional.ofNullable(definitions.get(name));
	}

	@Override
	public boolean isDefined(String name) {
		return definitions.containsKey(name) || exit().filter(p -> p.isDefined(name)).isPresent();
	}
}
