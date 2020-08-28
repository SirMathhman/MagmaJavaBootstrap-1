package com.meti.compile.type;

public enum TypeGroup {
	Function;

	public boolean matches(TypeGroup typeGroup) {
		return typeGroup == this;
	}
}
