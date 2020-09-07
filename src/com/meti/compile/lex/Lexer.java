package com.meti.compile.lex;

import com.meti.compile.node.Token;
import com.meti.compile.type.Type;
import com.meti.util.MonadOption;

public interface Lexer {
	MonadOption<Token> parseToOption(String content);

	@Deprecated
	Token parse(String content);

	Type resolve(String name);
}
