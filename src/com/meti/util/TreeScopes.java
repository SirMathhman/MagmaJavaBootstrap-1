package com.meti.util;

import com.meti.MagmaException;
import com.meti.compile.type.Type;

import java.util.Optional;

public class TreeScopes implements Scopes {
	private final Scope root = new TreeScope();
	private Scope current = root;

	@Override
	public void define(String name, Type type) {
		current.define(name, type);
	}

	@Override
	public void enter() {
		current = root.enter();
	}

	@Override
	public void exit() {
		current = current.exit().orElseThrow(() -> new MagmaException("Cannot exit, is at root."));
	}

	@Override
	public Optional<Type> lookup(String name) {
		return current.getDefinition(name);
	}

	@Override
	public boolean isDefined(String name) {
		return current.isDefined(name);
	}
}
