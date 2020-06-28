package com.meti;

import com.meti.compile.node.Node;
import com.meti.compile.parse.Parser;
import com.meti.compile.unit.StructUnit;
import com.meti.util.Structures;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StructUnitTest {

	@Test
	void parse() {
		Parser parser = new StructUnit(new Structures());
		Node node = parser.parse("struct Node {}", null);
		assertEquals("struct Node{}", node.render());
	}
}