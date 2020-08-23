package com.meti.compile.parse.block;

import com.meti.compile.Lexer;
import com.meti.compile.node.Node;
import com.meti.compile.node.block.ReturnNode;
import com.meti.compile.parse.FilteredLexRule;

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