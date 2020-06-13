package org.magma.parse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import org.magma.Extractor;
import org.magma.JSONUnit;

import java.util.Optional;

/**
 * Parsers floats using {@link Float#parseFloat(String)}.
 * The content is truncated if it ends with the character "f".
 * If content similar to "10.0", "5.2", "3.87" -- doesn't end with an "f",
 * then that content is assumed to be valid as a float and not a double.
 */
public class FloatParser extends JSONUnit implements Parser {
	@Inject
	public FloatParser(ObjectMapper mapper) {
		super(mapper);
	}

	/**
	 * Attempts to parse the content using {@link Float#parseFloat(String)},
	 * and returns an empty result if a {@link NumberFormatException} was thrown.
	 *
	 * @param content   <p>The content that is to be parsed.
	 *                  Should already be trimmed using {@link String#trim()}, and
	 *                  should not be null.
	 *                  </p>
	 * @param extractor
	 * @return The wrapped float.
	 */
	@Override
	public Optional<JsonNode> parse(String content, Extractor extractor) {
		if (content.endsWith("d")) return Optional.empty();
		try {
			String contentToUse = format(content);
			float result = Float.parseFloat(contentToUse);
			return buildNode(result);
		} catch (NumberFormatException e) {
			return Optional.empty();
		}
	}

	private static String format(String content) {
		return content.endsWith("f") ? content.substring(0,
				content.length() - 1) : content;
	}

	private Optional<JsonNode> buildNode(float result) {
		return Optional.of(createObject()
				.put("type", "float")
				.put("value", result));
	}
}
