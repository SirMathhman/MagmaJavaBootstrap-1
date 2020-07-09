package com.meti.util;

public interface Binding<T> {
	T get();

	void set(T value);
}
