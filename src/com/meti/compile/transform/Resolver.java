package com.meti.compile.transform;

import com.meti.compile.node.Node;
import com.meti.compile.node.NodeGroup;
import com.meti.compile.type.Type;

public class Resolver {
	public Type resolve(Node node) {
		if (node.applyToGroup(NodeGroup.Variable::matches)) {
			//TODO: node properties
		}
		throw new UnsupportedOperationException();
	}
}
