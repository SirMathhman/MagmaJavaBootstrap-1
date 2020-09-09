package com.meti.compile.process;

import com.meti.compile.node.Node;
import com.meti.compile.node.NodeGroup;

public interface Processor {
	boolean canProcess(NodeGroup group);

	Node process(Node node);
}
