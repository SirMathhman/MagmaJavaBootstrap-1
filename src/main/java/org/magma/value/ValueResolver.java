package org.magma.value;

import com.fasterxml.jackson.databind.JsonNode;
import org.magma.Extractor;

import java.util.Optional;

public interface ValueResolver {
	Optional<JsonNode> resolveValue(String value, Extractor extractor);
}
