package org.magma.core;

import java.util.Collections;
import java.util.Map;

public interface Compiler {
	default String compile(String content) {
		return compile(Collections.singletonMap("", content), "");
	}

	String compile(Map<String, String> content, String main);
}