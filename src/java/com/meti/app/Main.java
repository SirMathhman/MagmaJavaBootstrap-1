package com.meti.app;

import com.meti.task.Task;

import java.util.Collection;

public final class Main {
	static TaskSupplier supplier = new MagmaTaskSupplier();

	private Main() {
	}

	public static void main(String[] args) {
		Collection<Task> tasks = supplier.getTasks();
		tasks.forEach(Task::perform);
	}
}
