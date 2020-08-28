package com.meti.compile.process;

import com.meti.compile.node.Node;
import com.meti.compile.node.NodeGroup;

public interface Processor {
	boolean canModify(NodeGroup group);

	Node modify(Node node);
}
