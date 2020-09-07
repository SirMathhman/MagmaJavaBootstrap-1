package com.meti.compile.lex.parse.primitive;

import com.meti.compile.lex.Lexer;
import com.meti.compile.lex.parse.FilteredTokenizerFactory;
import com.meti.compile.node.Token;
import com.meti.compile.node.primitive.CharToken;

public class CharTokenizerFactory extends FilteredTokenizerFactory {
	@Override
	public boolean canQualify(String content) {
		return content.startsWith("'")
		       && content.endsWith("'")
		       && 3 == content.length();
	}

	@Override
	public Token parseQualified(String content, Lexer lexer) {
		return new CharToken(content.charAt(1));
	}
}
