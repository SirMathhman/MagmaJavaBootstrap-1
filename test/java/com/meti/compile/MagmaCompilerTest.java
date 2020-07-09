package com.meti.compile;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MagmaCompilerTest {
	private static Compiler compiler = null;

	@BeforeAll
	static void setUp() {
		compiler = MagmaCompiler.INSTANCE;
	}

	@Test
	void blocks() {
		assertEquals("{1020}", compiler.compile("{10;20}"));
	}

	@Test
	void declare() {
		assertEquals("x:Int;", compiler.compile("x : Int"));
	}

	@Test
	void declareWithBoth() {
		assertEquals("x:Int=10;", compiler.compile("x : Int = 10"));
	}

	@Test
	void declareWithValue() {
		assertEquals("x:?=10;", compiler.compile("x = 10"));
	}

	@Test
	void intName() {
		assertEquals("Int", compiler.compile("Int"));
	}

	@Test
	void integers() {
		assertEquals("10", compiler.compile("10"));
	}

	@Test
	void variables() {
		assertEquals("test", compiler.compile("test"));
	}
}