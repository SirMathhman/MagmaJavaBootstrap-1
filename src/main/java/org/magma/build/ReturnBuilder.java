package org.magma.build;

import com.fasterxml.jackson.databind.JsonNode;
import org.magma.exception.AssemblyException;

import java.util.Optional;

public class ReturnBuilder implements Builder {
	@Override
	public Optional<String> build(JsonNode node, Builder parent) {
		if (Builder.is(node, "return")) {
			JsonNode value = node.get("value");
			String valueString = parent.build(value, parent)
					.orElseThrow(() -> new AssemblyException("Failed to assemble return value:" + value));
			return Optional.of("return " + valueString + ";");
		}
		return Optional.empty();
	}
}
