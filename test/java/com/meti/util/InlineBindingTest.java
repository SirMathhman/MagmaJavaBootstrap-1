package com.meti.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InlineBindingTest {

	@Test
	void get() {
		Binding<String> binding = new InlineBinding<>("test");
		assertEquals("test", binding.get());
	}

	@Test
	void set() {
		Binding<String> binding = new InlineBinding<>();
		binding.set("test");
		assertEquals("test", binding.get());
	}
}