package com.meti.compile.transform;

import com.meti.compile.node.Node;
import com.meti.compile.node.NodeGroup;

public interface Modifier {
	boolean canModify(NodeGroup group);

	Node modify(Node node);
}
