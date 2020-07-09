package com.meti.app.properties;

import com.meti.log.Recorder;
import com.meti.task.SevereTask;
import com.meti.util.Binding;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class PropertiesLoadTask extends SevereTask {
	private final Path path;
	private final Binding<? super Properties> properties;

	public PropertiesLoadTask(Recorder recorder, Binding<? super Properties> properties, Path path) {
		super(recorder);
		this.properties = properties;
		this.path = path;
	}

	@Override
	protected void performImpl() throws IOException {
		try (InputStream input = Files.newInputStream(path)) {
			Properties value = new Properties();
			value.load(input);
			properties.set(value);
		}
	}

	@Override
	protected String failureMessage() {
		return "Failed to load properties from " + path.toAbsolutePath();
	}
}
