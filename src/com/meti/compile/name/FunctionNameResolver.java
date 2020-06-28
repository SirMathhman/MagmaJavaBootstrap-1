package com.meti.compile.name;

import com.meti.compile.Compiler;
import com.meti.compile.type.FunctionType;
import com.meti.compile.type.Type;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FunctionNameResolver implements NameResolver {
	@Override
	public boolean canResolve(String name) {
		return name.startsWith("(") && name.contains("=>");
	}

	@Override
	public Type resolveName(String name, Compiler compiler) {
		int returnIndex = name.indexOf("=>");
		String paramString = name.substring(0, returnIndex).trim();
		List<Type> parameters = resolveParameters(paramString, compiler);
		String returnString = name.substring(returnIndex + 2).trim();
		Type returnType = compiler.resolveName(returnString);
		return new FunctionType(parameters, returnType);
	}

	private static List<Type> resolveParameters(String paramString, Compiler compiler) {
		return Arrays.stream(paramString.substring(1, paramString.length() - 1)
				.split(","))
				.filter(s -> !s.isBlank())
				.map(String::trim)
				.map(compiler::resolveName)
				.collect(Collectors.toList());
	}
}