package com.meti;

import java.util.Collection;
import java.util.Collections;

public class CollectiveParseRule extends CompoundParseRule {
	final Collection<ParseRule> rules;

	public CollectiveParseRule(Collection<ParseRule> rules) {
		this.rules = Collections.unmodifiableCollection(rules);
	}

	@Override
	public Collection<ParseRule> supplyRules() {
		return rules;
	}
}