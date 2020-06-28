package com.meti.compile.parse;

import com.meti.compile.Compiler;
import com.meti.compile.node.InlineNode;
import com.meti.compile.node.Node;

public class ReturnParser implements Parser {
	@Override
	public boolean canParse(String content) {
		return content.startsWith("return ");
	}

	@Override
	public Node parse(String content, Compiler compiler) {
		String value = content.substring(7).trim();
		String output = compiler.parse(value).render();
		String result = "return " + output + ";";
		return new InlineNode(result);
	}
}