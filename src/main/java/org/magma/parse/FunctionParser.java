package org.magma.parse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import org.magma.Extractor;
import org.magma.JSONUnit;
import org.magma.util.NameProvider;
import org.magma.util.Scope;

import java.util.Optional;

public class FunctionParser extends JSONUnit implements Parser {
	private final NameProvider provider;
	private final Scope scope;

	@Inject
	public FunctionParser(ObjectMapper mapper, NameProvider provider, Scope scope) {
		super(mapper);
		this.provider = provider;
		this.scope = scope;
	}

	@Override
	public Optional<JsonNode> parse(String content, Extractor extractor) {
		if (content.startsWith("(")) {
			int end = content.indexOf(')');
			int marker = content.indexOf("=>");
			String paramsString = content.substring(1, end);
			ArrayNode parameters = createArray();
			for (String paramString : paramsString.split(",")) {
				if (!paramString.isBlank()) {
					int split = paramString.indexOf(':');
					String nameString = paramString.substring(0, split).trim();
					String typeString = paramString.substring(split + 1).trim();
					JsonNode type = extractor.resolveName(typeString);
					scope.define(nameString, type);
					parameters.add(createObject()
							.put("name", nameString)
							.set("instance", type));
				}
			}
			String returnString = content.substring(end + 1, marker).trim();
			JsonNode returnType = extractor.resolveName(returnString.substring(1).trim());
			JsonNode contentNode = extractor.parse(content.substring(marker + 2).trim());
			return Optional.ofNullable(createObject()
					.<ObjectNode>set("parameters", parameters)
					.<ObjectNode>set("return", returnType)
					.<ObjectNode>set("content", contentNode)
					.put("name", provider.nextName()));
		}
		return Optional.empty();
	}
}
