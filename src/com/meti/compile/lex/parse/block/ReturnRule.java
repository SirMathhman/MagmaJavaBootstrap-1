package com.meti.compile.lex.parse.block;

import com.meti.compile.lex.Lexer;
import com.meti.compile.lex.parse.FilteredLexRule;
import com.meti.compile.node.Token;
import com.meti.compile.node.block.ReturnToken;

public class ReturnRule extends FilteredLexRule {
	@Override
	public boolean canQualify(String content) {
		return content.startsWith("return ");
	}

	@Override
	public Token parseQualified(String content, Lexer lexer) {
		String value = content.substring(7);
		String formatted = value.trim();
		Token token = lexer.parse(formatted);
		return new ReturnToken(token);
	}
}