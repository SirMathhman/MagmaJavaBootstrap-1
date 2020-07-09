package com.meti.compile.node;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringNodeTest {
	@Test
	void isExpanded() {
		assertFalse(new StringNode().isParsed());
	}
}