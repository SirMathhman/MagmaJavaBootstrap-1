package org.magma.parse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import org.magma.Extractor;
import org.magma.JSONUnit;
import org.magma.exception.AssemblyException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DeclareParser extends JSONUnit implements Parser {
	@Inject
	public DeclareParser(ObjectMapper mapper) {
		super(mapper);
	}

	@Override
	public Optional<JsonNode> parse(String content, Extractor extractor) {
		if (hasType(content) || hasInitialValue(content)) {
			int equals = -1;
			for (int i = 0; i < content.toCharArray().length; i++) {
				if ('=' == content.charAt(i)) {
					String returnString = content.substring(i, i + 2);
					if (!"=>".equals(returnString)) {
						equals = i;
						break;
					}
				}
			}
			JsonNode initial = null;
			JsonNode initialType = null;
			if (-1 != equals) {
				String initialString = content.substring(equals + 1).trim();
				initial = extractor.parse(initialString);
				initialType = extractor.resolveValue(initialString);
			}
			String header = -1 == equals ? content : content.substring(0, equals).trim();
			int colon = header.indexOf(':');
			String keyString = -1 == colon ? header : header.substring(0, colon).trim();
			int lastSpace = keyString.lastIndexOf(' ');
			List<String> flags = parseFlags(keyString, lastSpace);
			if (flags.contains("val") || flags.contains("var")) {
				ArrayNode flagNode = wrapFlagsInNode(flags);
				String nameString = keyString.substring(lastSpace + 1).trim();
				JsonNode type = -1 == colon ? passAsInitial(initialType) : extractType(extractor, header, colon);
				return Optional.of(createObject()
						.put("type", "declaration")
						.put("name", nameString)
						.<ObjectNode>set("instance", type)
						.<ObjectNode>set("initial", initial)
						.set("flags", flagNode));
			}
		}
		return Optional.empty();
	}

	private static boolean hasType(String content) {
		return content.contains(":");
	}

	private static boolean hasInitialValue(String content) {
		return content.contains("=");
	}

	private static List<String> parseFlags(String keyString, int lastSpace) {
		String flagString = -1 == lastSpace ? keyString : keyString.substring(0, lastSpace).trim();
		return Arrays.stream(flagString.split(" "))
				.filter(s -> !s.isBlank())
				.map(String::trim)
				.map(String::toLowerCase)
				.collect(Collectors.toList());
	}

	private ArrayNode wrapFlagsInNode(Iterable<String> keys) {
		//replace with reduce?
		ArrayNode flagNode = createArray();
		keys.forEach(flagNode::add);
		return flagNode;
	}

	private static JsonNode passAsInitial(JsonNode initialType) {
		JsonNode type;
		if (null == initialType) {
			throw new AssemblyException("Neither a type or an initial value is present.");
		}
		type = initialType;
		return type;
	}

	private static JsonNode extractType(Extractor extractor, String header, int colon) {
		String trim = header.substring(colon + 1).trim();
		return extractor.resolveName(trim);
	}
}