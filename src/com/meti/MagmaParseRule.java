package com.meti;

import com.meti.block.BlockRule;
import com.meti.block.FunctionParseRule;
import com.meti.block.ReturnRule;

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
