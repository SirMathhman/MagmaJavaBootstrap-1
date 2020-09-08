package com.meti.compile.node;

import com.meti.compile.type.Field;
import com.meti.util.MonadStream;

import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public final class EmptyDependents implements Dependents {
	public static final EmptyDependents Empty = new EmptyDependents();

	private EmptyDependents() {
	}

	@Override
	public <T> T apply(BiFunction<List<Field>, List<Token>, T> function) {
		return function.apply(Collections.emptyList(), Collections.emptyList());
	}

	@Override
	public Dependents copyChildren(List<Token> children) {
		return Empty;
	}

	@Override
	public Dependents copyFields(List<Field> fields) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Stream<Token> streamChildrenNatively() {
		return Stream.empty();
	}

	@Override
	public Stream<Field> streamFieldsNatively() {
		return Stream.empty();
	}

	@Override
	public MonadStream<Token> streamChildren(){
		throw new UnsupportedOperationException();
	}

    @Override
    public Dependents identity(){
        throw new UnsupportedOperationException();
    }

	@Override
	public Dependents append(Token child){
		throw new UnsupportedOperationException();
	}
}
