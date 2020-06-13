package org.magma.core;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class MagmaCompilerTest {

	@Test
	void compile() {
		String result = MagmaCompiler.INSTANCE.compile(Collections.singletonMap("main", "10"), "main");
		assertEquals("10", result);
	}
}