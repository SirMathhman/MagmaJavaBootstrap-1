package org.magma.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.magma.core.MagmaCompiler.INSTANCE;

class MagmaCompilerTest {
	@Test
	void doubles() {
		assertEquals("10.0d", INSTANCE.compile("10d"));
	}

	@Test
	void floats() {
		assertEquals("10.0f", INSTANCE.compile("10.0"));
		assertEquals("10.0f", INSTANCE.compile("10f"));
	}

	@Test
	void function() {
		assertEquals("int root(){}", INSTANCE.compile("() : Int => {}"));
	}

	@Test
	void integers() {
		assertEquals("10", INSTANCE.compile("10"));
	}
}