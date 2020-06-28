package com.meti.compile.type;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public final class FunctionType implements Type {
	private final Collection<? extends Type> parameters;
	private final Type returnType;

	public FunctionType(Collection<? extends Type> parameters, Type returnType) {
		this.parameters = Collections.unmodifiableCollection(parameters);
		this.returnType = returnType;
	}

	@Override
	public String render() {
		return render("");
	}

	@Override
	public String render(String name) {
		String paramString = renderParameters();
		return returnType.render("(*" + name + ")" + paramString);
	}

	private String renderParameters() {
		return parameters.stream()
				.map(Type::render)
				.collect(Collectors.joining(",", "(", ")"));
	}
}
