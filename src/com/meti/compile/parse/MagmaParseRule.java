package com.meti.compile.parse;

import com.meti.compile.parse.block.BlockRule;
import com.meti.compile.parse.block.FunctionParseRule;
import com.meti.compile.parse.block.ReturnRule;
import com.meti.compile.parse.primitive.CharParseRule;
import com.meti.compile.parse.primitive.IntParseRule;
import com.meti.compile.parse.scope.DeclareParseRule;
import com.meti.compile.parse.scope.VariableParseRule;
import com.meti.compile.util.CallStack;

import java.util.Collection;
import java.util.List;

public class MagmaParseRule extends CompoundParseRule {
	private final CallStack stack = new CallStack();

	@Override
	public Collection<ParseRule> supplyRules() {
		return List.of(
				new BlockRule(),
				new ReturnRule(),
				new DeclareParseRule(stack),
				new FunctionParseRule(),
				new CharParseRule(),
				new IntParseRule(),
				new VariableParseRule(stack)
		);
	}
}
