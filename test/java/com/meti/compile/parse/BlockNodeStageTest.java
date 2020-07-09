package com.meti.compile.parse;

import com.meti.compile.node.StringNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BlockNodeStageTest {

	@Test
	void canParse() {
		NodeStage nodeStage = new BlockNodeStage();
		assertTrue(nodeStage.canAccept(new StringNode("{}")));
	}
}