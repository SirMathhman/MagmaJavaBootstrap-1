package com.meti.compile.lex.parse.primitive;

import com.meti.compile.lex.Lexer;
import com.meti.compile.lex.parse.FilteredLexRule;
import com.meti.compile.node.Token;
import com.meti.compile.node.primitive.CharToken;

public class CharLexRule extends FilteredLexRule {
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
