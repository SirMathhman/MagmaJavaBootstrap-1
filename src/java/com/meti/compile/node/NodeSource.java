package com.meti.compile.node;

@FunctionalInterface
public interface NodeSource {
	Node parse(Node node);
}