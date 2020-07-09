package com.meti.log;

import com.meti.task.ExecutableTask;

public abstract class AbstractRecorder implements Recorder {
	@Override
	public boolean severe(ExecutableTask<? extends Throwable> task, String message) {
		try {
			task.execute();
			return false;
		} catch (Throwable e) {
			severe(e, message);
			return true;
		}
	}

	@Override
	public boolean warning(ExecutableTask<? extends Throwable> task, String message) {
		try {
			task.execute();
			return false;
		} catch (Throwable e) {
			warning(e, message);
			return true;
		}
	}

	@Override
	public void warning(String message) {
		//TODO: implement warning with message only
	}
}
