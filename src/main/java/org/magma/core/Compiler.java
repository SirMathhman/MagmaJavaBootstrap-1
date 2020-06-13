package org.magma.core;

import java.util.Collection;
import java.util.Map;

public interface Compiler {
	Collection<String> compile(Map<String, String> content);
}