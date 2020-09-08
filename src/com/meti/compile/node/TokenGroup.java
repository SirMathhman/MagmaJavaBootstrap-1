package com.meti.compile.node;

public enum TokenGroup {
	Function,
	Variable,
	Declare,
	Initial,
	Return,
	Block,
	Char,
	Int, Import, Invocation, Line, Infix, Structure, StructDeclare, Field, Value;

	public boolean matches(TokenGroup other) {
		return this == other;
	}
}
