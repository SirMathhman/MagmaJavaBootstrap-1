package com.meti.compile;

import com.meti.compile.lex.Lexer;
import com.meti.compile.lex.RootLexer;
import com.meti.compile.lex.parse.LexRule;
import com.meti.compile.lex.parse.MagmaLexRule;
import com.meti.compile.lex.resolve.MagmaResolveRule;
import com.meti.compile.lex.resolve.ResolveRule;
import com.meti.compile.node.Node;
import com.meti.compile.transform.Transformer;
import com.meti.compile.transform.TypeTransformer;

public class MagmaCompiler implements Compiler {
	private final LexRule rootParserRule = new MagmaLexRule();
	private final ResolveRule rootResolveRule = new MagmaResolveRule();
	private final Lexer lexer = new RootLexer(rootParserRule, rootResolveRule);
	private final Transformer transformer = new TypeTransformer();

	@Override
	public String compileImpl(String value) {
		String wrapped = "{" + value + "}";
		Node root = lexer.parse(wrapped);
		Node transform = transformer.transform(root);
		String result = transform.render();
		return result.substring(1, result.length() - 1);
	}
}