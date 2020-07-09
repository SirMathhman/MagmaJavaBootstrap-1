package com.meti.compile.node;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LeafNodeTest {

	@Test
	void isExpanded() {
		Node node = new IntNode(10);
		assertTrue(node.isParsed());
	}
}