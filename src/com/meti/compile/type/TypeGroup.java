package com.meti.compile.type;

public enum TypeGroup {
	Function, Pointer;

	public boolean matches(TypeGroup typeGroup) {
		return typeGroup == this;
	}
}
