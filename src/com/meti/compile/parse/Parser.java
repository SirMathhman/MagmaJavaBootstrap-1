package com.meti.compile.parse;

import com.meti.compile.Compiler;
import com.meti.compile.node.Node;

public interface Parser {
	boolean canParse(String content);

	Node parse(String content, Compiler compiler);
}
