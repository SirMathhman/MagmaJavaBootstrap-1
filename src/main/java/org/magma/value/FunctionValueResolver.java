package org.magma.value;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import org.magma.Extractor;
import org.magma.JSONUnit;

import java.util.Optional;

public class FunctionValueResolver extends JSONUnit implements ValueResolver {

	@Inject
	public FunctionValueResolver(ObjectMapper mapper) {
		super(mapper);
	}

	@Override
	public Optional<JsonNode> resolveValue(String value, Extractor extractor) {
		if (value.startsWith("(")) {
			int end = value.indexOf(')');
			int marker = value.indexOf("=>");
			String paramsString = value.substring(1, end);
			ArrayNode parameters = createArray();
			for (String paramString : paramsString.split(",")) {
				if (!paramString.isBlank()) {
					int split = paramString.indexOf(':');
					String typeString = paramString.substring(split + 1).trim();
					JsonNode type = extractor.resolveName(typeString);
					parameters.add(type);
				}
			}
			String returnString = value.substring(end + 1, marker).trim();
			JsonNode returnType = extractor.resolveName(returnString.substring(1).trim());
			return Optional.ofNullable(createObject()
					.<ObjectNode>set("parameters", parameters)
					.<ObjectNode>set("return", returnType)
					.put("type", "function"));
		}
		return Optional.empty();
	}
}
