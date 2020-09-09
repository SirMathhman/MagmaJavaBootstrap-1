package com.meti.compile.instance;

public enum TypeGroup {
	Function, Pointer, Structure;

	public boolean matches(TypeGroup typeGroup) {
		return typeGroup == this;
	}
}
