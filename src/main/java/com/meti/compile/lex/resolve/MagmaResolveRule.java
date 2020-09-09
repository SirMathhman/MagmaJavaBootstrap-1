package com.meti.compile.lex.resolve;

import com.meti.compile.lex.resolve.primitive.*;

import java.util.Collection;
import java.util.List;

public class MagmaResolveRule extends CompoundResolveRule {
	@Override
	public Collection<ResolveRule> supplyRules() {
		return List.of(
				new CharResolveRule(),
				new IntResolveRule(),
				new StringResolveRule(),
				new AnyResolveRule(),
				new VoidResolveRule(),
				new StructResolveRule()
		);
	}
}
