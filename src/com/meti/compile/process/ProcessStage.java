package com.meti.compile.process;

import com.meti.compile.node.Node;

public interface ProcessStage {
	Node process(Node node);
}
