package com.meti.compile.parse.scope;

import com.meti.compile.Compiler;
import com.meti.compile.node.Node;
import com.meti.compile.node.scope.DeclareNode;
import com.meti.compile.node.scope.InitialNode;
import com.meti.compile.parse.FilteredParseRule;
import com.meti.compile.type.Type;
import com.meti.compile.type.primitive.PrimitiveType;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class DeclareParseRule extends FilteredParseRule {
	public static DeclareNode buildDeclare(String name, Type type) {
		return new DeclareNode(name, type);
	}

	@Override
	public Node parseQualified(String content, Compiler compiler) {
		String name = parseName(content);
		Type type = parseType(content, compiler);
		return -1 == content.indexOf('=')
				? buildDeclare(name, type)
				: buildInitial(content, compiler, name, type);
	}

	public static InitialNode buildInitial(String content, Compiler compiler, String name, Type type) {
		int equals = content.indexOf('=');
		String valueString = content.substring(equals + 1).trim();
		Node value = compiler.parse(valueString);
		return new InitialNode(name, type, value);
	}

	public static String parseName(String content) {
		String header = extractHeader(content);
		int separator = header.lastIndexOf(' ');
		return header.substring(separator + 1).trim();
	}

	public static Type parseType(String content, Compiler compiler) {
		//const x = 10;
		return -1 == content.indexOf(':') ?
				PrimitiveType.Unknown :
				parseTypePresent(content, compiler);
	}

	public static Type parseTypePresent(String content, Compiler compiler) {
		String typeString = -1 == content.indexOf('=')
				? content.substring(content.indexOf(':'))
				: content.substring(content.indexOf(':'), content.indexOf('='));
		String formattedTypeString = typeString.trim();
		return compiler.resolve(formattedTypeString);
	}

	@Override
	public boolean canQualify(String content) {
		String header = extractHeader(content);
		int separator = header.lastIndexOf(' ');
		if (-1 == separator) return false;
		List<String> flags = parseFlags(header, separator);
		return hasFeatures(content) && hasFlags(flags);
	}

	public static String extractHeader(String content) {
		int headerEnd = -1 == content.indexOf(':') ?
				content.indexOf('=') :
				content.indexOf(':');
		return content.substring(0, headerEnd);
	}

	public static List<String> parseFlags(String header, int separator) {
		return Arrays.stream(header.substring(0, separator).split(" "))
				.filter(s -> !s.isBlank())
				.map(String::trim)
				.collect(Collectors.toList());
	}

	public static boolean hasFeatures(String content) {
		return content.contains(":") || content.contains("=");
	}

	public static boolean hasFlags(Collection<String> flags) {
		return flags.contains("const") || flags.contains("let");
	}
}
