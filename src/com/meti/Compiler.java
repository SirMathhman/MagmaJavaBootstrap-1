package com.meti;

public class Compiler {
	private final ParseRule functionParser = new FunctionParseRule();
	private final ParseRule returnRule = new ReturnRule();

	public String parse(String content) {
		Node node;
		if (returnRule.canQualify(content)) {
			node = returnRule.parse(content, this);
		} else if (functionParser.canQualify(content)) {
			node = functionParser.parse(content, this);
		} else {
			throw new ParseException("Failed to parse: " + content);
		}
		return node.render();
	}

}