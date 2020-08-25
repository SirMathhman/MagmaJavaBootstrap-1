package com.meti.compile;

import com.meti.compile.type.Type;

import java.util.Deque;
import java.util.LinkedList;

public class TypeStack {
	private final Deque<Type> expectedTypes = new LinkedList<>();

	public void push(Type type) {
		expectedTypes.push(type);
	}
}
