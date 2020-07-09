package com.meti.compile.node;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlockNodeTest {
	@Test
	void isExpandedFalse() {
		Node node = new BlockNode(new StringNode());
		assertFalse(node.isParsed());
	}

	@Test
	void isExpandedTrue() {
		Node node = new BlockNode(new IntNode(10));
		assertTrue(node.isParsed());
	}
}