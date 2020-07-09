package com.meti.compile.node;

import com.meti.MagmaException;
import com.meti.compile.parse.*;

import java.util.Collection;
import java.util.List;

public class MagmaNodeSource implements NodeSource {
	private final Collection<NodeStage> nodeStages = List.of(
			new BlockNodeStage(),
			new IntNodeStage(),
			new DeclareNodeStage(),
			new VariableNodeStage()
	);

	@Override
	public Node parse(Node node) {
		return nodeStages.stream()
				.filter(parser -> parser.canAccept(node))
				.map(parser -> parser.accept(node))
				.findFirst()
				.orElseThrow(() -> new MagmaException("Failed to accept: " + node));
	}
}
