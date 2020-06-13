package org.magma.parse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.magma.Compiler;

import java.util.Optional;

public class FunctionParser extends JSONUnit implements Parser {
	protected FunctionParser(ObjectMapper mapper) {
		super(mapper);
	}

	@Override
	public Optional<JsonNode> parse(String content, Compiler compiler) {
		return Optional.empty();
	}
}
