package com.meti.compile.parse.primitive;

import com.meti.compile.Compiler;
import com.meti.compile.node.Node;
import com.meti.compile.node.primitive.CharNode;
import com.meti.compile.parse.FilteredParseRule;

public class CharParseRule extends FilteredParseRule {
	@Override
	public boolean canQualify(String content) {
		return content.startsWith("'")
		       && content.endsWith("'")
		       && 3 == content.length();
	}

	@Override
	public Node parseQualified(String content, Compiler compiler) {
		return new CharNode(content.charAt(1));
	}
}
