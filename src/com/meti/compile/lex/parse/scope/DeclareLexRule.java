package com.meti.compile.lex.parse.scope;

import com.meti.compile.lex.Lexer;
import com.meti.compile.lex.parse.FilteredLexRule;
import com.meti.compile.node.Node;
import com.meti.compile.node.scope.DeclareNode;
import com.meti.compile.node.scope.InitialNode;
import com.meti.compile.node.scope.InitialNodeBuilder;
import com.meti.compile.type.Type;
import com.meti.compile.type.primitive.PrimitiveType;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class DeclareLexRule extends FilteredLexRule {
	@Override
	public Node parseQualified(String content, Lexer lexer) {
		String name = parseName(content);
		Type type = parseType(content, lexer);
		return -1 == content.indexOf('=')
				? buildDeclare(name, type)
				: buildInitial(content, lexer, name, type);
	}

	@Override
	public boolean canQualify(String content) {
		if (!hasFeatures(content)) return false;
		String header = extractHeader(content);
		int separator = header.lastIndexOf(' ');
		if (-1 == separator) return false;
		List<String> flags = parseFlags(header, separator);
		return hasFlags(flags);
	}

	public static String parseName(String content) {
		String header = extractHeader(content);
		int separator = header.lastIndexOf(' ');
		return header.substring(separator + 1).trim();
	}

	public static InitialNode buildInitial(String content, Lexer lexer, String name, Type type) {
		int equals = content.indexOf('=');
		String valueString = content.substring(equals + 1).trim();
		Node value = lexer.parse(valueString);
		return new InitialNodeBuilder().withName(name).withType(type).withValue(value).build();
	}

	public static Type parseType(String content, Lexer lexer) {
		//const x = 10;
		return -1 == content.indexOf(':') ?
				PrimitiveType.Implicit :
				parseTypePresent(content, lexer);
	}

	public static DeclareNode buildDeclare(String name, Type type) {
		return new DeclareNode(name, type);
	}

	public static Type parseTypePresent(String content, Lexer lexer) {
		String typeString = -1 == content.indexOf('=')
				? content.substring(content.indexOf(':') + 1)
				: content.substring(content.indexOf(':') + 1, content.indexOf('='));
		String formattedTypeString = typeString.trim();
		return lexer.resolve(formattedTypeString);
	}

	public static boolean hasFeatures(String content) {
		return content.contains(":") || content.contains("=");
	}

	public static String extractHeader(String content) {
		int headerEnd = -1 == content.indexOf(':') ?
				content.indexOf('=') :
				content.indexOf(':');
		return content.substring(0, headerEnd).trim();
	}

	public static List<String> parseFlags(String header, int separator) {
		return Arrays.stream(header.substring(0, separator).split(" "))
				.filter(s -> !s.isBlank())
				.map(String::trim)
				.collect(Collectors.toList());
	}

	public static boolean hasFlags(Collection<String> flags) {
		return flags.contains("const") || flags.contains("let");
	}
}
