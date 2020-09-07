package com.meti.compile.lex;

import com.meti.compile.node.Token;
import com.meti.compile.type.Type;

public interface Lexer {
	Token parse(String content);

	Type resolve(String name);
}
