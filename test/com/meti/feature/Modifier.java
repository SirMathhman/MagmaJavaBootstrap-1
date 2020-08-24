package com.meti.feature;

import com.meti.compile.node.Node;
import com.meti.compile.node.NodeGroup;

public interface Modifier {
	boolean canModify(NodeGroup nodeGroup);

	Node modify(Node copy);
}
