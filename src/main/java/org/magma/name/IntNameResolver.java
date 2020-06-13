package org.magma.name;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import org.magma.Compiler;
import org.magma.parse.JSONUnit;

import java.util.Optional;

public class IntNameResolver extends JSONUnit implements NameResolver {
	@Inject
	protected IntNameResolver(ObjectMapper mapper) {
		super(mapper);
	}

	@Override
	public Optional<JsonNode> resolveName(String name, Compiler compiler) {
		return Optional.of(name)
				.filter("Int"::equals)
				.map(this::toNode);
	}

	private ObjectNode toNode(String n) {
		return createObject().put("name", n);
	}
}
