package com.meti.compile.type;

public interface Type {
	default String render() {
		return render("");
	}

	String render(String name);
}
