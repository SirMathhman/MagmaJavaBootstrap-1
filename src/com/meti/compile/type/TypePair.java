package com.meti.compile.type;

import com.meti.compile.node.Renderable;

import java.util.function.BiFunction;

public interface TypePair extends Renderable {
	<T> T map(BiFunction<String, Type, T> function);
}
