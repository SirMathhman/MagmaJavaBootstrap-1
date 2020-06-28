package com.meti;

import com.meti.compile.*;
import com.meti.compile.Compiler;
import com.meti.compile.parse.BlockParser;
import com.meti.compile.parse.Parser;
import com.meti.compile.value.ValueResolver;
import com.meti.util.TreeScopes;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class BlockParserTest {

	@Test
	void parse() {
		Parser parser =new BlockParser(new TreeScopes());
		Compiler compiler =new UnitCompiler(Collections.singletonList(parser), Collections.emptyList(), new ArrayList<ValueResolver>());
		String result = parser.parse("{{};{}}", compiler).render();
		assertEquals("{{}{}}", result);
	}
}