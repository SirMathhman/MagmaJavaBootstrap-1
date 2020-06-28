package com.meti;

import com.meti.compile.*;
import com.meti.compile.Compiler;
import com.meti.compile.name.FunctionNameResolver;
import com.meti.compile.name.IntNameResolver;
import com.meti.compile.node.Node;
import com.meti.compile.parse.*;
import com.meti.util.TreeScopes;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FunctionParserTest {
	private final Compiler compiler = new UnitCompiler(List.of(new IntParser(), new ReturnParser(),
			new BlockParser(new TreeScopes())), List.of(new FunctionNameResolver(), new IntNameResolver()), new ArrayList<>());

	@Test
	void parse() {
		Parser parser = new FunctionParser(new TreeScopes());
		Node node = parser.parse("int main() {return 0;}", compiler);
		assertEquals("int main(){return 0;}", node.render());
	}
}