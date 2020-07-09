package com.meti.log;

import com.meti.task.ExecutableTask;

public interface Recorder {
	boolean severe(ExecutableTask<? extends Throwable> task, String message);

	void severe(Throwable e, String message);

	boolean warning(ExecutableTask<? extends Throwable> task, String message);

	void warning(Throwable e, String message);

	void warning(String message);
}
