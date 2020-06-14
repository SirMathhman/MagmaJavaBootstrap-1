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
		assertEquals("int root(){}", new MagmaCompiler().compile("() : Int => {}"));
	}

	@Test
	void functionWithParameter(){
		assertEquals("int root(int x){}", new MagmaCompiler().compile("(x : Int) : Int => {}"));
	}

	@Test
	void functionWithContent(){
		assertEquals("int root(int x){return x;}", new MagmaCompiler().compile("(x : Int) : Int => {return x;}"));
	}

//	@Test
//	void main(){
//		assertEquals("int main(){return 0;}", INSTANCE.compile("val main = () : Int => {return 0;}"));
//	}

	@Test
	void integers() {
		assertEquals("10", INSTANCE.compile("10"));
	}
}