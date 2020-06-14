package org.magma.core;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.magma.Extractor;
import org.magma.InjectedExtractor;
import org.magma.build.*;
import org.magma.exception.AssemblyException;
import org.magma.name.IntNameResolver;
import org.magma.name.VoidNameResolver;
import org.magma.parse.*;
import org.magma.value.FunctionValueResolver;

import java.util.Map;

public class MagmaCompiler implements Compiler {
	public static final Compiler INSTANCE = new MagmaCompiler();
	private final MagmaModule module = new MagmaModule();
	private final Injector injector = Guice.createInjector(module);
	private final Builder builder = InjectedBuilder.create(injector,
			VariableBuilder.class,
			ReturnBuilder.class,
			BlockBuilder.class,
			FunctionBuilder.class,
			DoubleBuilder.class,
			FloatBuilder.class,
			IntBuilder.class,
			IntTypeBuilder.class);
	private final Extractor extractor = InjectedExtractor.create(injector,
			FunctionValueResolver.class,
			IntNameResolver.class,
			VoidNameResolver.class,
			BlockParser.class,
			CharParser.class,
			DeclareParser.class,
			IntParser.class,
			FloatParser.class,
			DoubleParser.class,
			FunctionParser.class,
			ReturnParser.class,
			VariableParser.class);

	@Override
	public String compile(Map<String, String> content, String main) {
		if (!content.containsKey(main))
			throw new IllegalArgumentException("The specified imports don't have the main path.");
		String mainContent = content.get(main);
		JsonNode parse = extractor.parse(mainContent);
		return builder.build((ObjectNode) parse, null)
				.orElseThrow(() -> new AssemblyException("Failed to assemble: " + parse));
	}
}
