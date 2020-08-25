package com.meti.compile.transform;

import com.meti.compile.node.Node;

public interface Transformer {
	Node transform(Node node);
}
