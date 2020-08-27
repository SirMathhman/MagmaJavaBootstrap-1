package com.meti.compile.transform;

import com.meti.compile.node.Node;
import com.meti.compile.node.NodeGroup;

public interface Loader {
	boolean canLoad(NodeGroup group);

	void load(Node node);
}
