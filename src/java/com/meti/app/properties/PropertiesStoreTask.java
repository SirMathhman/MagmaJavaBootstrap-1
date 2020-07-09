package com.meti.app.properties;

import com.meti.log.Recorder;
import com.meti.task.SevereTask;
import com.meti.util.Binding;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class PropertiesStoreTask extends SevereTask {
	private final Binding<? extends Properties> binding;
	private final Path path;

	public PropertiesStoreTask(Recorder recorder, Binding<? extends Properties> binding, Path path) {
		super(recorder);
		this.binding = binding;
		this.path = path;
	}

	@Override
	protected void performImpl() throws IOException {
		try (OutputStream outputStream = Files.newOutputStream(path)) {
			Properties properties = binding.get();
			properties.store(outputStream, "");
		}
	}

	@Override
	protected String failureMessage() {
		return "Failed to store properties to " + path.toAbsolutePath();
	}
}
