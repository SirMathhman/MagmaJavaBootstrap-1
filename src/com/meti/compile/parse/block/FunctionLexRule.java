package com.meti.compile.parse.block;

import com.meti.compile.Lexer;
import com.meti.compile.node.Node;
import com.meti.compile.node.block.FunctionNodeBuilder;
import com.meti.compile.parse.FilteredLexRule;
import com.meti.compile.type.Type;
import com.meti.compile.type.TypePair;

import java.util.ArrayList;

public class FunctionLexRule extends FilteredLexRule {
	@Override
	public boolean canQualify(String content) {
		return content.startsWith("def");
	}

	@Override
	public Node parseQualified(String content, Lexer lexer) {
		int paramStart = content.indexOf('(');
		String name = content.substring(4, paramStart).trim();
		int returnSeparator = content.indexOf(':');
		int valueSeparator = content.indexOf("=>");
		String returnString = content.substring(returnSeparator + 1, valueSeparator)
				.trim();
		Type returnType = lexer.resolve(returnString);
		Node value = content.substring(valueSeparator + 2)
				.trim()
				.transform(lexer::parse);
		return new FunctionNodeBuilder().withName(name).withReturnType(returnType).withValue(value).withParameters(new ArrayList<TypePair>()).build();
	}
}