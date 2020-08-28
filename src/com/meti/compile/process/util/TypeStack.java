package com.meti.compile.process.util;

import com.meti.compile.type.Type;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;

public class TypeStack {
	private final Deque<Type> expectedTypes = new LinkedList<>();

	public Optional<Type> peek() {
		if (expectedTypes.isEmpty()) return Optional.empty();
		else return Optional.ofNullable(expectedTypes.peek());
	}

	public Optional<Type> pop() {
		if (expectedTypes.isEmpty()) return Optional.empty();
		return Optional.ofNullable(expectedTypes.pop());
	}

	public void push(Type type) {
		expectedTypes.push(type);
	}

	@Override
	public String toString() {
		return "TypeStack{" +
		       "expectedTypes=" + expectedTypes +
		       '}';
	}
}
