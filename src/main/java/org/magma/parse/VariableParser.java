package org.magma.parse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import org.magma.Compiler;
import org.magma.JSONUnit;
import org.magma.util.Scope;

import java.util.Optional;

public class VariableParser extends JSONUnit implements Parser {
	private final Scope scope;

	@Inject
	public VariableParser(ObjectMapper mapper, Scope scope) {
		super(mapper);
		this.scope = scope;
	}

	@Override
	public Optional<JsonNode> parse(String content, Compiler compiler) {
		return Optional.of(content)
				.filter(scope::isDefined)
				.map(this::buildNode);
	}

	private ObjectNode buildNode(String s) {
		return createObject()
				.put("type", "variable")
				.put("value", s);
	}
}
