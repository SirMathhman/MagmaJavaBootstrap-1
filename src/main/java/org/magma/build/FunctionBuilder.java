package org.magma.build;

import com.fasterxml.jackson.databind.JsonNode;
import org.magma.exception.AssemblyException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FunctionBuilder implements Builder {
	@Override
	public Optional<String> build(JsonNode node, Builder parent) {
		if (Builder.is(node, "function")) {
			List<String> renderedParameters = new ArrayList<>();
			JsonNode parameters = node.get("parameters");
			for (JsonNode parameter : parameters) {
				String name = parameter.get("name").asText();
				JsonNode instance = parameter.get("instance");
				String result = parent.build(instance, parent)
						.orElseThrow(() -> new AssemblyException("Failed to assemble parameter type: " + instance));
				renderedParameters.add(String.format(result, name));
			}
			JsonNode returnNode = node.get("return");
			String returnType = parent.build(returnNode, parent)
					.orElseThrow(() -> new AssemblyException("Failed to build return type: " + returnNode));
			JsonNode content = node.get("content");
			String contentString = parent.build(content, parent)
					.orElseThrow(() -> new AssemblyException("Failed to build content: " + content));
			String name = node.get("name").asText();
			return Optional.of(String.format(returnType, name + "(" + String.join(",", renderedParameters) + ")") + contentString);
		}
		return Optional.empty();
	}
}
