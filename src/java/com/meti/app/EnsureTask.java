package com.meti.app;

import com.meti.log.Recorder;
import com.meti.task.SevereTask;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class EnsureTask extends SevereTask {
	private final Path config;

	public EnsureTask(Recorder recorder, Path config) {
		super(recorder);
		this.config = config;
	}

	@Override
	protected void performImpl() throws IOException {
		if (!Files.exists(config)) {
			Files.createFile(config);
		}
	}

	@Override
	protected String failureMessage() {
		return "Failed to create configuration file.";
	}
}
