package com.meti.compile.process;

import com.meti.compile.node.Node;
import com.meti.compile.node.NodeGroup;

public interface Preprocessor {
	boolean canPreprocess(NodeGroup group);

	Node preprocess(Node node);
}
