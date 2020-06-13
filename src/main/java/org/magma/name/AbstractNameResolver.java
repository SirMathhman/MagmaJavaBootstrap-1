package org.magma.name;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.magma.JSONUnit;

public abstract class AbstractNameResolver extends JSONUnit implements NameResolver {
	public AbstractNameResolver(ObjectMapper mapper) {
		super(mapper);
	}

	protected ObjectNode wrapInNode(String n) {
		return createObject().put("name", n);
	}
}
