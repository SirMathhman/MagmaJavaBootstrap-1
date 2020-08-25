package com.meti.compile.lex.parse.block;

import com.meti.compile.lex.Lexer;
import com.meti.compile.lex.parse.FilteredLexRule;
import com.meti.compile.node.Node;
import com.meti.compile.node.block.ReturnNode;

public class ReturnRule extends FilteredLexRule {
	@Override
	public boolean canQualify(String content) {
		return content.startsWith("return ");
	}

	@Override
	public Node parseQualified(String content, Lexer lexer) {
		String valueString = content.substring(7).trim();
		Node value = lexer.parse(valueString);
		return new ReturnNode(value);
	}
}