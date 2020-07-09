package com.meti.compile.parse;

import com.meti.compile.node.IntNode;
import com.meti.compile.node.Node;

public class IntNodeStage implements NodeStage {
	@Override
	public boolean canAccept(Node node) {
		return node.getValue()
				.filter(IntNodeStage::isInt)
				.isPresent();
	}

	private static boolean isInt(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	@Override
	public Node accept(Node node) {
		String value = node.getValue().orElseThrow();
		int intValue = Integer.parseInt(value);
		return new IntNode(intValue);
	}
}
