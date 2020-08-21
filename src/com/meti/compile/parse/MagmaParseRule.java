package com.meti.compile.parse;

import com.meti.compile.parse.block.BlockRule;
import com.meti.compile.parse.block.FunctionParseRule;
import com.meti.compile.parse.block.ReturnRule;
import com.meti.compile.parse.primitive.IntParseRule;

import java.util.Collection;
import java.util.List;

public class MagmaParseRule extends CompoundParseRule {
	@Override
	public Collection<ParseRule> supplyRules() {
		return List.of(
				new BlockRule(),
				new ReturnRule(),
				new FunctionParseRule(),
				new IntParseRule()
		);
	}
}
