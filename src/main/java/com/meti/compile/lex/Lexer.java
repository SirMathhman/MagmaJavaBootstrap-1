package com.meti.compile.lex;

import com.meti.compile.node.Token;
import com.meti.compile.instance.Type;
import com.meti.util.Option;

public interface Lexer {
	Option<Token> parseToOption(String content);

	@Deprecated
	Token parse(String content);

	Type resolve(String name);
}
