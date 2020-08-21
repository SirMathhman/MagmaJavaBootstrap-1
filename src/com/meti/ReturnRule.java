package com.meti;

public class ReturnRule implements ParseRule {
	@Override
	public boolean canQualify(String content) {
		return content.startsWith("return ");
	}

	@Override
	public Node parse(String content, Compiler compiler) {
		String valueString = content.substring(7).trim();
		String value = compiler.parse(valueString);
		return new ReturnNode(value);
	}
}