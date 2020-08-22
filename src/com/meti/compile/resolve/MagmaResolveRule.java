package com.meti.compile.resolve;

import com.meti.compile.resolve.primitive.CharResolveRule;
import com.meti.compile.resolve.primitive.IntResolveRule;

import java.util.Collection;
import java.util.List;

public class MagmaResolveRule extends CompoundResolveRule {
	@Override
	public Collection<ResolveRule> supplyRules() {
		return List.of(
				new CharResolveRule(),
				new IntResolveRule()
		);
	}
}
