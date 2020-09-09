package com.meti.compile.type;

public enum TypeGroup {
	Function, Pointer, Structure;

	public boolean matches(TypeGroup typeGroup) {
		return typeGroup == this;
	}
}
