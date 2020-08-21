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
	@Override
	public boolean canQualify(String content) {
		String header = extractHeader(content);
		int separator = header.lastIndexOf(' ');
		if (separator == -1) return false;
		List<String> flags = Arrays.stream(header.substring(0, separator).split(" "))
				.filter(s -> !s.isBlank())
				.map(String::trim)
				.collect(Collectors.toList());
		return hasFeatures(content) && hasFlags(flags);
	}

	@Override
	public Node parseQualified(String content, Compiler compiler) {
		String name = parseName(content);
		Type type = parseType(content, compiler);
		return -1 == content.indexOf('=')
				? buildDeclare(name, type)
				: buildInitial(content, compiler, name, type);
	}

	public String parseName(String content) {
		String header = extractHeader(content);
		int separator = header.lastIndexOf(' ');
		String name = header.substring(separator + 1).trim();
		return name;
	}

	public Type parseType(String content, Compiler compiler) {
		Type type;
		//const x = 10;
		if (-1 == content.indexOf(':')) {
			type = PrimitiveType.Unknown;
		} else {
			String typeString = -1 == content.indexOf('=')
					? content.substring(content.indexOf(':'))
					: content.substring(content.indexOf(':'), content.indexOf('='));
			String formattedTypeString = typeString.trim();
			type = compiler.resolve(formattedTypeString);
		}
		return type;
	}

	public static DeclareNode buildDeclare(String name, Type type) {
		return new DeclareNode(name, type);
	}

	public static InitialNode buildInitial(String content, Compiler compiler, String name, Type type) {
		int equals = content.indexOf('=');
		String valueString = content.substring(equals + 1).trim();
		Node value = compiler.parse(valueString);
		return new InitialNode(name, type, value);
	}

	public String extractHeader(String content) {
		int headerEnd = -1 == content.indexOf(':') ?
				content.indexOf('=') :
				content.indexOf(':');
		String header = content.substring(0, headerEnd);
		return header;
	}

	public static boolean hasFeatures(String content) {
		return content.contains(":") || content.contains("=");
	}

	public static boolean hasFlags(Collection<String> flags) {
		return flags.contains("const") || flags.contains("let");
	}
}
