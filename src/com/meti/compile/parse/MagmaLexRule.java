package com.meti.compile.parse;

import com.meti.compile.parse.block.BlockRule;
import com.meti.compile.parse.block.FunctionLexRule;
import com.meti.compile.parse.block.ReturnRule;
import com.meti.compile.parse.primitive.CharLexRule;
import com.meti.compile.parse.primitive.IntLexRule;
import com.meti.compile.parse.scope.DeclareLexRule;
import com.meti.compile.parse.scope.VariableLexRule;

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
