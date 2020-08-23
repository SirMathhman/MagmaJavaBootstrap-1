package com.meti.feature;

import com.meti.compile.Lexer;
import com.meti.compile.RootLexer;
import com.meti.compile.node.Node;
import com.meti.compile.parse.LexRule;
import com.meti.compile.parse.MagmaLexRule;
import com.meti.compile.resolve.MagmaResolveRule;
import com.meti.compile.resolve.ResolveRule;

public class MagmaCompiler implements Compiler {
	@Override
	public String compileImpl(String value) {
		LexRule rootParserRule = new MagmaLexRule();
		ResolveRule rootResolveRule = new MagmaResolveRule();
		Lexer lexer = new RootLexer(rootParserRule, rootResolveRule);
		Node root = lexer.parse(value);
		return root.render();
	}
}