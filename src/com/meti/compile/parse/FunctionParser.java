package com.meti.compile.parse;

import com.meti.compile.Compiler;
import com.meti.compile.node.Node;
import com.meti.compile.type.FunctionType;
import com.meti.compile.type.Type;
import com.meti.util.Scopes;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class FunctionParser implements Parser {
	private final Scopes scopes;

	public FunctionParser(Scopes scopes) {
		this.scopes = scopes;
	}

	@Override
	public boolean canParse(String content) {
		boolean hasParameters = hasParameters(content);
		boolean hasContent = hasContent(content);
		return hasParameters && hasContent && hasParametersBeforeContent(content);
	}

	private static boolean hasParameters(String content) {
		int paramStart = content.indexOf('(');
		int paramEnd = content.indexOf(')');
		return -1 != paramStart &&
		       -1 != paramEnd &&
		       paramStart < paramEnd;
	}

	private static boolean hasContent(String content) {
		int contentStart = content.indexOf('{');
		int contentEnd = content.indexOf('}');
		return -1 != contentStart &&
		       -1 != contentEnd &&
		       contentStart < contentEnd;
	}

	private static boolean hasParametersBeforeContent(String content) {
		int paramStart = content.indexOf('(');
		int paramEnd = content.indexOf(')');
		int contentStart = content.indexOf('{');
		int contentEnd = content.indexOf('}');
		return isBeforeBoth(paramStart, contentStart, contentEnd) &&
		       isBeforeBoth(paramEnd, contentStart, contentEnd);
	}

	private static boolean isBeforeBoth(int index, int first, int second) {
		return index < first && index < second;
	}

	@Override
	public Node parse(String content, Compiler compiler) {
		int bracket = content.indexOf('{');
		int start = content.indexOf('(');
		int end = content.indexOf(')');
		int space = content.substring(0, start).lastIndexOf(' ');
		String returnName = content.substring(0, space).trim();
		Type returnType = compiler.resolveName(returnName);
		String name = content.substring(space + 1, start);
		String paramsString = content.substring(start + 1, end);
		Map<String, Type> parameters = parseParameters(paramsString, compiler);
		String after = content.substring(bracket).trim();
		scopes.define(name, new FunctionType(parameters.values(), returnType));
		parameters.forEach(scopes::define);
		Node parse = compiler.parse(after);
		return new FunctionNode(name, returnType, parameters, parse);
	}

	public static Map<String, Type> parseParameters(String paramsString, Compiler compiler) {
		return Arrays.stream(paramsString.split(","))
				.filter(s -> !s.isBlank())
				.map(String::trim)
				.collect(Collectors.toMap(
						FunctionParser::parseParameterName,
						param -> parseParameterType(param, compiler)));
	}

	private static String parseParameterName(String paramString) {
		String trim = paramString.trim();
		int last = trim.lastIndexOf(' ');
		return trim.substring(last + 1).trim();
	}

	private static Type parseParameterType(String paramString, Compiler compiler) {
		String trim = paramString.trim();
		int last = trim.lastIndexOf(' ');
		return compiler.resolveName(trim.substring(0, last).trim());
	}

	private static final class FunctionNode implements Node {
		private final Node content;
		private final String name;
		private final Map<String, Type> parameters;
		private final Type returnType;

		private FunctionNode(String name, Type returnType, Map<String, Type> parameters, Node content) {
			this.name = name;
			this.returnType = returnType;
			this.parameters = parameters;
			this.content = content;
		}

		@Override
		public String render() {
			String paramString = parameters.keySet()
					.stream()
					.map(param -> parameters.get(param).render(param))
					.collect(Collectors.joining(",", "(", ")"));
			return returnType.render(name + paramString + content.render());
		}
	}
}