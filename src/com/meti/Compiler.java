package com.meti;

public interface Compiler {
	Node parse(String content);

	Type resolve(String name);
}
