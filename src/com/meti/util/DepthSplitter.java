package com.meti.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.BiPredicate;

public class DepthSplitter implements Splitter {
	public static final Splitter BRACKET = new DepthSplitter('{', '}',
			(depth1, c1) -> ';' == c1 && 0 == depth1 || c1 == '}' && 1 == depth1);
	private final char decrease;
	private final char increase;
	private final BiPredicate<? super Integer, ? super Character> shouldSplit;

	public DepthSplitter(char increase, char decrease, BiPredicate<? super Integer, ? super Character> shouldSplit) {
		this.increase = increase;
		this.decrease = decrease;
		this.shouldSplit = shouldSplit;
	}

	@Override
	public Collection<String> split(CharSequence value) {
		Collection<String> items = new ArrayList<>();
		StringBuilder builder = new StringBuilder();
		int depth = 0;
		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			if (shouldSplit.test(depth, c)) {
				if (c == decrease && 1 == depth) {
					builder.append(c);
					depth--;
				}
				items.add(builder.toString());
				builder = new StringBuilder();
			} else {
				if (increase == c) depth++;
				if (decrease == c) depth--;
				builder.append(c);
			}
		}
		items.add(builder.toString());
		items.removeIf(String::isBlank);
		return items;
	}
}