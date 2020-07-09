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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Locale;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class PropertiesStoreTaskTest {
	private static final Path PATH = Paths.get(".", "test", "test");
	private static final ThreadLocal<DateFormat> format =
			ThreadLocal.withInitial(PropertiesStoreTaskTest::buildPattern);
	private static final Properties properties = new Properties();
	private static final String separator = System.lineSeparator();
	private static final String AFTER = separator + "testKey=testValue" + separator;
	private static final String BEFORE = "#" + separator + "#";

	private static SimpleDateFormat buildPattern() {
		return new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy", Locale.ENGLISH);
	}

	@BeforeAll
	static void setUp() throws IOException {
		Files.deleteIfExists(PATH);

		properties.setProperty("testKey", "testValue");
	}

	@AfterAll
	static void tearDown() throws IOException {
		Files.deleteIfExists(PATH);
	}

	@Test
	void perform() throws IOException {
		Recorder recorder = new ConsumableRecorder(Collections.singletonMap(RecordLevel.SEVERE, null));
		Binding<Properties> binding = new InlineBinding<>(properties);
		Task task = new PropertiesStoreTask(recorder, binding, PATH);
		assertFalse(task.perform());
		assertEquals(1, properties.size());
		String text = Files.readString(PATH);
		assertTrue(text.startsWith(BEFORE));
		assertTrue(text.endsWith(AFTER));

		String dateString = text.substring(text.indexOf(BEFORE) + BEFORE.length(), text.indexOf(AFTER));
		assertDoesNotThrow(() -> format.get().parse(dateString));
	}
}