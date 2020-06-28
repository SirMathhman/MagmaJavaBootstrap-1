package com.meti;

import com.meti.compile.*;
import com.meti.compile.name.IntNameResolver;
import com.meti.compile.name.NameResolver;
import com.meti.compile.name.PointerNameResolver;
import com.meti.compile.type.Type;
import com.meti.compile.value.ValueResolver;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class PointerNameResolverTest {

	@Test
	void resolveName() {
		NameResolver resolver = new PointerNameResolver();
		Type type = resolver.resolveName("int*", new UnitCompiler(Collections.emptyList(),
				Collections.singletonList(new IntNameResolver()), new ArrayList<ValueResolver>()));
		assertEquals("int *test", type.render("test"));
	}
}