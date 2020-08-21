package com.meti.block;

import com.meti.Compiler;
import com.meti.FilteredParseRule;
import com.meti.Node;
import com.meti.Type;

public class FuAnctionParseRule implements FilteredParseRule {
	@Override
	public boolean canQualify(String content) {
		return content.startsWith("def");
	}

	@Override
	public Node parseQualified(String content, Compiler compiler) {
		int paramStart = content.indexOf('(');
		String name = content.substring(4, paramStart).trim();
		int returnSeparator = content.indexOf(':');
		int valueSeparator = content.indexOf("=>");
		String returnString = content.substring(returnSeparator + 1, valueSeparator)
				.trim();
		Type returnType = compiler.resolve(returnString);
		Node value = content.substring(valueSeparator + 2)
				.trim()
				.transform(compiler::parse);
		return new FunctionNode(name, returnType, value);
	}
}