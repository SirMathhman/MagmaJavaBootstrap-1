package com.meti.task;

import com.meti.log.Recorder;

public abstract class WarningTask implements Task {
	private final Recorder recorder;

	protected WarningTask(Recorder recorder) {
		this.recorder = recorder;
	}

	@Override
	public boolean perform() {
		return recorder.warning(this::performImpl, failureMessage());
	}

	protected abstract void performImpl() throws Throwable;

	protected abstract String failureMessage();
}
