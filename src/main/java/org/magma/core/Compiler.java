package org.magma.core;

import java.util.Map;

public interface Compiler {
	String compile(Map<String, String> content, String main);
}