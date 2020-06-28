package com.meti.compile;

import com.meti.compile.node.Node;
import com.meti.compile.type.Type;

public interface Compiler {
	Node parse(String content);

	Type resolveName(String name);

	Type resolveValue(String value);
}
