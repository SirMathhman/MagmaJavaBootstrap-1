package com.meti.compile.node;

public enum NodeGroup {
	Function,
	Variable,
	Declare,
	Initial,
	Return,
	Block,
	Char,
	Int, Import, Invocation, Line, Infix, Structure;

	public boolean matches(NodeGroup other) {
		return this == other;
	}
}
