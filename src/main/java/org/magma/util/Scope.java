package org.magma.util;

import com.fasterxml.jackson.databind.JsonNode;

public interface Scope {
	void define(String name, JsonNode type);

	void enter(String name);

	void exit();

	boolean isDefined(String name);
}
