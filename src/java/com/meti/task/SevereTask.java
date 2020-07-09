package com.meti.task;

import com.meti.log.Recorder;

import java.io.IOException;

public abstract class SevereTask implements Task {
	private final Recorder recorder;

	protected SevereTask(Recorder recorder) {
		this.recorder = recorder;
	}

	@Override
	public boolean perform() {
		return recorder.severe(this::performImpl, failureMessage());
	}

	protected abstract void performImpl() throws IOException;

	protected abstract String failureMessage();
}
