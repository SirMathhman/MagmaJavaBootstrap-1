package com.meti.compile.parse;

import com.meti.compile.node.Node;

public interface NodeStage {
	Node accept(Node node);

	boolean canAccept(Node node);
}
