package com.meti.compile.type;

import java.util.stream.Stream;

public interface Type {
	default String render() {
		return render("");
	}

	String render(String name);

	Stream<Type> streamChildren();
}
