package org.magma.core;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.magma.core.MagmaCompiler.INSTANCE;

class MagmaCompilerTest {

	@Test
	void compile() {
		String result = INSTANCE.compile("10");
		assertEquals("10", result);
	}
}