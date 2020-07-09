package com.meti.app;

import com.meti.task.Task;

import java.util.Collection;

public interface TaskSupplier {
	Collection<Task> getTasks();
}
