package com.meti.util;

public class InlineBinding<T> implements Binding<T> {
	private T value;

	public InlineBinding() {
		this(null);
	}

	public InlineBinding(T value) {
		this.value = value;
	}

	@Override
	public T get() {
		return value;
	}

	@Override
	public void set(T value) {
		this.value = value;
	}
}
