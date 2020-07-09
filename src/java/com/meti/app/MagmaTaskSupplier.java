package com.meti.app;

import com.meti.app.properties.PropertiesEnsureTask;
import com.meti.app.properties.PropertiesLoadTask;
import com.meti.app.properties.PropertiesStoreTask;
import com.meti.log.LoggedRecorder;
import com.meti.log.Recorder;
import com.meti.task.Task;
import com.meti.util.Binding;
import com.meti.util.InlineBinding;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class MagmaTaskSupplier implements TaskSupplier {
	private static final Path CONFIG = Paths.get(".", "config.properties");
	private final Properties properties = new Properties();
	private final Binding<Properties> binding = new InlineBinding<>(properties);
	private final Recorder recorder = new LoggedRecorder();

	@Override
	public Collection<Task> getTasks() {
		return List.of(
				new EnsureTask(recorder, CONFIG),
				new PropertiesLoadTask(recorder, binding, CONFIG),
				new PropertiesEnsureTask(binding, "Source-Directory", "source"),
				new PropertiesEnsureTask(binding, "Main-Class", "Main.magma"),
				new CompileTask(recorder, binding),
				new PropertiesStoreTask(recorder, binding, CONFIG)
		);
	}
}