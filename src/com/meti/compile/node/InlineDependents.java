package com.meti.compile.node;

import com.meti.compile.type.TypePair;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public final class InlineDependents implements Dependents {
	private final List<Node> children;
	private final List<TypePair> fields;

	private InlineDependents(List<TypePair> fields, List<Node> children) {
		this.fields = Collections.unmodifiableList(fields);
		this.children = Collections.unmodifiableList(children);
	}

	public static InlineDependents ofChild(Node value) {
		return ofChildren(Collections.singletonList(value));
	}

	public static InlineDependents ofChildren(List<Node> children) {
		return of(Collections.emptyList(), children);
	}

	public static InlineDependents of(List<TypePair> fields, List<Node> children) {
		return new InlineDependents(fields, children);
	}

	public static InlineDependents ofSingleton(TypePair pair, Node node) {
		return of(Collections.singletonList(pair), Collections.singletonList(node));
	}

	@Override
	public <T> T apply(BiFunction<List<TypePair>, List<Node>, T> function) {
		return function.apply(fields, children);
	}

	@Override
	public Dependents copyChildren(List<Node> children) {
		return of(fields, children);
	}

	@Override
	public Stream<Node> streamChildren() {
		return children.stream();
	}

	@Override
	public Stream<TypePair> streamFields() {
		return applyToFields(Collection::stream);
	}

	private <T> T applyToFields(Function<List<TypePair>, T> mapper) {
		return mapper.apply(fields);
	}
}
