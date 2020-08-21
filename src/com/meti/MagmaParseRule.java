package com.meti;

import java.util.Collection;
import java.util.List;

public class MagmaParseRule extends CompoundParseRule {
	@Override
	public Collection<ParseRule> supplyRules() {
		return List.of(
				new ReturnRule(),
				new FunctionParseRule()
		);
	}
}
