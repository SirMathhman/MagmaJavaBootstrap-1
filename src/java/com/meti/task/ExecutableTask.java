package com.meti.task;

public interface ExecutableTask<E extends Throwable> {
	void execute() throws E;
}
