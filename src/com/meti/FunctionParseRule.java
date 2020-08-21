package com.meti;

public class FunctionParseRule implements FilteredParseRule {
	@Override
	public boolean canParse(String content) {
		return content.startsWith("def");
	}

	@Override
	public Node parseImpl(String content, Compiler compiler) {
		int paramStart = content.indexOf('(');
		String name = content.substring(4, paramStart).trim();
		int returnSeparator = content.indexOf(':');
		int valueSeparator = content.indexOf("=>");
		String returnString = content.substring(returnSeparator + 1, valueSeparator)
				.trim();
		Type returnType = compiler.resolve(returnString);
		Node value = content.substring(valueSeparator + 1)
				.trim()
				.transform(compiler::parse);
		return new FunctionNode(name, returnType, value);
	}
}