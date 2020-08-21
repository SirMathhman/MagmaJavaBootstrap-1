package com.meti.compile.util;

import com.meti.compile.type.Type;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;

public final class CallStack {
	private final Deque<StackFrame> frames = new LinkedList<>();

	public CallStack() {
		enter();
	}

	public void enter() {
		this.frames.add(new StackFrame());
	}

	public void enter(Map<String, Type> definitions) {
		enter();
		definitions.forEach(this::define);
	}

	public String define(String name, Type type) {
		return frames.peek().define(name, type);
	}

	public void exit() {
		this.frames.pop();
	}

	public boolean isDefined(String name) {
		return frames.stream().anyMatch(frame -> frame.isDefined(name));
	}
}
