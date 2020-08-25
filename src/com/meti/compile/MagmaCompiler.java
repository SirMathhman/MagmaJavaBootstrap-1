package com.meti.compile;

import com.meti.compile.node.Node;
import com.meti.compile.parse.LexRule;
import com.meti.compile.parse.MagmaLexRule;
import com.meti.compile.resolve.MagmaResolveRule;
import com.meti.compile.resolve.ResolveRule;

public class MagmaCompiler implements Compiler {
	private final LexRule rootParserRule = new MagmaLexRule();
	private final ResolveRule rootResolveRule = new MagmaResolveRule();
	private final Lexer lexer = new RootLexer(rootParserRule, rootResolveRule);

	@Override
	public String compileImpl(String value) {
		Node root = lexer.parse(value);
		Transformer transformer = new Transformer();
		Node transform = transformer.transform(root);
		return transform.render();
	}
}