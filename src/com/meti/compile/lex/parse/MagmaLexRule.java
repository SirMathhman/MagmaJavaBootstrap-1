package com.meti.compile.lex.parse;

import com.meti.compile.lex.parse.block.*;
import com.meti.compile.lex.parse.block.function.AbstractLexRule;
import com.meti.compile.lex.parse.block.function.ConcreteLexRule;
import com.meti.compile.lex.parse.external.ImportRule;
import com.meti.compile.lex.parse.primitive.CharLexRule;
import com.meti.compile.lex.parse.primitive.IntLexRule;
import com.meti.compile.lex.parse.scope.DeclareLexRule;
import com.meti.compile.lex.parse.scope.VariableLexRule;

import java.util.Collection;
import java.util.List;

public class MagmaLexRule extends CompoundLexRule {
	@Override
	public Collection<LexRule> supplyRules() {
		return List.of(
				new ImportRule(),
				new BlockRule(),
				new ReturnRule(),
				new InvocationRule(),
				new DeclareLexRule(),
				new ConcreteLexRule(),
				new AbstractLexRule(),
				new CharLexRule(),
				new IntLexRule(),
				new VariableLexRule()
		);
	}
}
