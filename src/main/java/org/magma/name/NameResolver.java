package org.magma.name;

import com.fasterxml.jackson.databind.JsonNode;
import org.magma.Compiler;

import java.util.Optional;

public interface NameResolver {
	Optional<JsonNode> resolveName(String name, Compiler compiler);
}
