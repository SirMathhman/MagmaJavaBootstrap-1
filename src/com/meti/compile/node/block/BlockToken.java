package com.meti.compile.node.block;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.InlineDependents;
import com.meti.compile.node.Token;
import com.meti.compile.node.TokenGroup;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BlockToken extends ParentToken {
	private final List<Token> children;

	public BlockToken(List<Token> children) {
		this.children = Collections.unmodifiableList(children);
	}

	@Override
	public <T> T applyToGroup(Function<TokenGroup, T> mapper) {
		return mapper.apply(TokenGroup.Block);
	}

	@Override
	public Dependents createDependents() {
		return InlineDependents.ofChildren(children);
	}

	@Override
	public Token copy(Dependents dependents) {
		return dependents.streamChildren()
				.reduce(new BlockNodeBuilder(),
						BlockNodeBuilder::append,
						(oldBuilder, newBuilder) -> newBuilder)
				.build();
	}

	@Override
	public String render() {
		return children.stream()
				.map(Token::render)
				.collect(Collectors.joining("", "{", "}"));
	}
}
