package com.meti.app.properties;

import com.meti.log.ConsumableRecorder;
import com.meti.log.RecordLevel;
import com.meti.log.Recorder;
import com.meti.task.Task;
import com.meti.util.Binding;
import com.meti.util.InlineBinding;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class PropertiesLoadTaskTest {
	private static final Path PATH = Paths.get(".", "test", "test");
	private static final Properties properties = new Properties();

	@BeforeAll
	static void setUp() throws IOException {
		Files.deleteIfExists(PATH);

		properties.setProperty("testKey", "testValue");
		try (OutputStream output = Files.newOutputStream(PATH)) {
			properties.store(output, "");
		}
	}

	@AfterAll
	static void tearDown() throws IOException {
		Files.deleteIfExists(PATH);
	}

	@Test
	void perform() {
		Recorder recorder = new ConsumableRecorder(Collections.singletonMap(RecordLevel.SEVERE, null));
		Binding<Properties> binding = new InlineBinding<>(properties);
		Task task = new PropertiesLoadTask(recorder, binding, PATH);
		assertFalse(task.perform());
		assertEquals(1, properties.size());
		assertEquals("testValue", properties.getProperty("testKey"));
	}
}