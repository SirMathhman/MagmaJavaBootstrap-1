package com.meti.compile.type;

import java.util.function.Function;
import java.util.stream.Stream;

public interface Type {
	<T> T applyToGroup(Function<TypeGroup, T> function);

	boolean matches(Type other);

	default String render() {
		return render("");
	}

	String render(String name);

	Stream<Type> streamChildren();
}
