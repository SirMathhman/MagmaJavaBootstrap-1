package org.magma.util;

import java.util.HashMap;
import java.util.Map;

public class ScopedProvider implements NameProvider {
	private final Map<String, Integer> counts = new HashMap<>();
	private final Scope scope;

	public ScopedProvider(Scope scope) {
		this.scope = scope;
	}

	@Override
	public String nextName() {
		String name = scope.currentName();
		if (counts.containsKey(name)) {
			Integer count = counts.get(name);
			return name + count;
		} else {
			counts.put(name, 0);
			return name;
		}
	}
}
