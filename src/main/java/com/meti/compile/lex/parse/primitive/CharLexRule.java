package com.meti.compile.lex.parse.primitive;

import com.meti.compile.lex.Lexer;
import com.meti.compile.lex.parse.FilteredLexRule;
import com.meti.compile.node.Node;
import com.meti.compile.node.primitive.CharNode;

public class CharLexRule extends FilteredLexRule {
	@Override
	public boolean canQualify(String content) {
		return content.startsWith("'")
		       && content.endsWith("'")
		       && 3 == content.length();
	}

	@Override
	public Node parseQualified(String content, Lexer lexer) {
		return new CharNode(content.charAt(1));
	}
}
