package com.meti;

import com.meti.compile.node.Node;
import com.meti.compile.node.StructNode;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class StructNodeTest {

	@Test
	void render() {
		Node node = new StructNode("test", Collections.emptyMap());
		assertEquals("struct test{}", node.render());
	}
}