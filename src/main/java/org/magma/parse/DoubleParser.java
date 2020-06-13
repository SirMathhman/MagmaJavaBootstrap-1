package org.magma.parse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import org.magma.Extractor;
import org.magma.JSONUnit;

import java.util.Optional;

/**
 * Parsers doubles using {@link Double#parseDouble(String)}.
 * The content is truncated if it ends with the character "d".
 * If content similar to "10.0", "5.2", "3.87" -- doesn't end with an "f",
 * then that content is assumed to be valid as a float and not a double.
 */
public class DoubleParser extends JSONUnit implements Parser {
	@Inject
	public DoubleParser(ObjectMapper mapper) {
		super(mapper);
	}

	/**
	 * Attempts to parse the content using {@link Double#parseDouble(String)},
	 * and returns an empty result if a {@link NumberFormatException} was thrown.
	 *
	 * @param content <p>The content that is to be parsed.
	 *                Should already be trimmed using {@link String#trim()}, and
	 *                should not be null.
	 *                </p>
	 * @param extractor
	 * @return The wrapped double.
	 */
	@Override
	public Optional<JsonNode> parse(String content, Extractor extractor) {
		if (isLackingIdentifier(content)) return Optional.empty();
		try {
			String formatted = content.substring(0, content.length() - 1);
			double result = Double.parseDouble(formatted);
			return buildNode(result);
		} catch (NumberFormatException e) {
			return Optional.empty();
		}
	}

	private static boolean isLackingIdentifier(String content) {
		return content.isEmpty() || 'd' != content.charAt(content.length() - 1);
	}

	private Optional<JsonNode> buildNode(double result) {
		return Optional.of(createObject()
				.put("type", "double")
				.put("value", result));
	}
}
