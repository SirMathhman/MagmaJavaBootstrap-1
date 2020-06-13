package org.magma.value;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import org.magma.Compiler;
import org.magma.JSONUnit;

import java.util.Optional;

public class IntValueResolver extends JSONUnit implements ValueResolver {
	@Inject
	protected IntValueResolver(ObjectMapper mapper) {
		super(mapper);
	}

	@Override
	public Optional<JsonNode> resolveValue(String value, Compiler compiler) {
		try {
			Integer.parseInt(value);
			return Optional.of(createObject()
					.put("name", "Int"));
		} catch (NumberFormatException e) {
			return Optional.empty();
		}
	}
}
