package com.meti.compile.parse;

import com.meti.compile.Compiler;
import com.meti.compile.node.InlineNode;
import com.meti.compile.node.Node;

public class IntParser implements Parser {
	@Override
	public boolean canParse(String content) {
		try {
			Integer.parseInt(content);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	@Override
	public Node parse(String content, Compiler compiler) {
		String result;
		result = String.valueOf(Integer.parseInt(content));
		return new InlineNode(result);
	}
}