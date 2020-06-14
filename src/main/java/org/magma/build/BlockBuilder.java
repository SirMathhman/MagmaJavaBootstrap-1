package org.magma.build;

import com.fasterxml.jackson.databind.JsonNode;
import org.magma.exception.AssemblyException;

import java.util.Optional;

public class BlockBuilder implements Builder {
	@Override
	public Optional<String> build(JsonNode node, Builder parent) {
		JsonNode children = node.get("children");
		if (!Builder.is(node, "block")) return Optional.empty();
		StringBuilder builder = new StringBuilder();
		for (JsonNode child : children) {
			builder.append(parent.build(child, parent)
					.orElseThrow(() -> new AssemblyException("Failed to build child: " + child)));
		}
		return Optional.of("{" + builder + "}");
	}
}
