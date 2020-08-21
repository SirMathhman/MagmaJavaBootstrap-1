package com.meti.block;

import com.meti.Compiler;
import com.meti.FilteredParseRule;
import com.meti.Node;

public class ReturnRule implements FilteredParseRule {
	@Override
	public boolean canQualify(String content) {
		return content.startsWith("return ");
	}

	@Override
	public Node parseQualified(String content, Compiler compiler) {
		String valueString = content.substring(7).trim();
		Node value = compiler.parse(valueString);
		return new ReturnNode(value);
	}
}