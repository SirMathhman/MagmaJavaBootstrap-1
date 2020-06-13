package org.magma.parse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.magma.Compiler;
import org.magma.exception.AssemblyException;

import java.util.Arrays;
import java.util.Optional;

public class DeclareParser extends JSONUnit implements Parser {
	protected DeclareParser(ObjectMapper mapper) {
		super(mapper);
	}

	@Override
	public Optional<JsonNode> parse(String content, Compiler compiler) {
		if (hasType(content) || hasInitialValue(content)) {
			int equals = content.indexOf('=');
			JsonNode initial = null;
			JsonNode initialType = null;
			if (-1 != equals) {
				String initialString = content.substring(equals + 1).trim();
				initial = compiler.parse(initialString);
				initialType = compiler.resolveValue(initialString);
			}
			String header = -1 == equals ? content : content.substring(0, equals).trim();
			int colon = header.indexOf(':');
			JsonNode type;
			if (-1 == colon) {
				if (null == initialType) {
					throw new AssemblyException("Neither a type or an initial value is present.");
				}
				type = initialType;
			} else {
				String trim = header.substring(colon + 1).trim();
				type = compiler.resolveName(trim);
			}
			String keyString = header.substring(0, colon).trim();
			int lastSpace = keyString.lastIndexOf(' ');
			String flagString = keyString.substring(0, lastSpace).trim();
			ArrayNode flagNode = createArray();
			Arrays.stream(flagString.split(" "))
					.filter(s -> !s.isBlank())
					.map(String::trim)
					.map(String::toLowerCase)
					.forEach(flagNode::add);
			String nameString = keyString.substring(lastSpace + 1).trim();
			return Optional.of(createObject()
					.put("type", "declaration")
					.put("name", nameString)
					.<ObjectNode>set("instance", type)
					.<ObjectNode>set("initial", initial)
					.set("flags", flagNode));
		}
		return Optional.empty();
	}

	private boolean hasType(String content) {
		return content.contains(":");
	}

	private boolean hasInitialValue(String content) {
		return content.contains("=");
	}
}