package com.meti.compile.parse;

import com.meti.compile.node.StringNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class IntNodeStageTest {

	@Test
	void canParse() {
		NodeStage nodeStage = new IntNodeStage();
		assertTrue(nodeStage.canAccept(new StringNode("10")));
	}
}