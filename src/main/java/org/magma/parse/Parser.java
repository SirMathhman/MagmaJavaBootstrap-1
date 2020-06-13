package org.magma.parse;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Optional;

/***
 * <p>
 *     Abstraction for turning strings into valid JSON nodes.
 *     We use JSON because it's much more effective at being
 *     trees. Also: we can remove details from the JSON and save
 *     it as a "header" file. This is handy because C doesn't really
 *     offer reflection.
 * </p>
 */
public interface Parser {
	/**
	 * <p>
	 * Takes in content and parses it. If the content cannot be parsed, an empty Optional is returned.
	 * An Optional is returned because, more often than not, an implementation of this interface will
	 * be grouped together with other implementations in a chain and looped over recursively. However,
	 * if this method were to throw an exception if invalid content were found, then the loop would
	 * either instantly terminate or would be uncomfortable to look at. Moreover, there will be much
	 * more content that is invalid than valid.
	 * </p>
	 *
	 * @param content <p>The content that is to be parsed.
	 *                Should already be trimmed using {@link String#trim()}, and
	 *                should not be null.
	 *                </p>
	 * @return <p>Returns an Optional containing the valid JSON nodes, or empty if the content was invalid.</p>
	 */
	Optional<JsonNode> parse(String content);
}
