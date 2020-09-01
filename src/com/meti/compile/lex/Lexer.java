package com.meti.compile.lex;

import com.meti.compile.node.Node;
import com.meti.compile.type.Type;

public interface Lexer {
	Node parse(String content);

	Type resolve(String name);
}
