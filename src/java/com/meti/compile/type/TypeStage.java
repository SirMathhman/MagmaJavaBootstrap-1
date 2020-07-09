package com.meti.compile.type;

public interface TypeStage {
	Type accept(Type type);

	boolean canAccept(Type type);
}