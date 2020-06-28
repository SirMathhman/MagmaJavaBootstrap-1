package com.meti.compile.type;

public final class PointerType implements Type {
	private final Type parent;

	public PointerType(Type parent) {
		this.parent = parent;
	}

	public Type getParent() {
		return parent;
	}

	@Override
	public String render(String name) {
		return parent.render("*" + name);
	}
}
