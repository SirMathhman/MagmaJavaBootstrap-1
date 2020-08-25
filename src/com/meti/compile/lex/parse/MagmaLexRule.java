package com.meti.compile.lex.parse;

import com.meti.compile.lex.parse.block.BlockRule;
import com.meti.compile.lex.parse.block.FunctionLexRule;
import com.meti.compile.lex.parse.block.ReturnRule;
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
				new BlockRule(),
				new ReturnRule(),
				new DeclareLexRule(),
				new FunctionLexRule(),
				new CharLexRule(),
				new IntLexRule(),
				new VariableLexRule()
		);
	}
}
