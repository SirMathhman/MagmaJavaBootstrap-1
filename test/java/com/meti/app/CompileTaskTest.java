package com.meti.app;

import com.meti.CompileException;
import com.meti.log.ConsumableRecorder;
import com.meti.log.RecordLevel;
import com.meti.log.Recorder;
import com.meti.task.Task;
import com.meti.util.Binding;
import com.meti.util.InlineBinding;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class CompileTaskTest {
	public static final Path SOURCE = Paths.get(".", "source");
	public static final Path MAIN = SOURCE.resolve("Main.magma");
	public static final Path TARGET = Paths.get(".", "target");

	@BeforeAll
	static void setUp() throws IOException {
		cleanup();
	}

	private static void cleanup() throws IOException {
		Files.deleteIfExists(MAIN);
		Files.deleteIfExists(SOURCE);
		Files.deleteIfExists(TARGET.resolve("out.c"));
		Files.deleteIfExists(TARGET);
	}

	@Test
	void compilePath() throws IOException {
		if (!Files.exists(SOURCE)) Files.createFile(SOURCE);
		String result = CompileTask.compilePath(SOURCE);
		assertEquals("", result);
	}

	@Test
	void getSourceDirectory() {
		Properties properties = new Properties();
		properties.setProperty("Source-Directory", "source");
		Path directory = CompileTask.getSourceDirectory(properties);
		assertEquals(SOURCE, directory);
		assertTrue(Files.exists(directory));
	}

	@Test
	void getTargetDirectory() {
		Path target = CompileTask.getTargetDirectory();
		assertEquals(TARGET, target);
		assertTrue(Files.exists(target));
	}

	@Test
	void mainExtant() throws IOException {
		Files.createDirectory(SOURCE);
		Files.createFile(MAIN);

		Properties properties = new Properties();
		properties.setProperty("Main-Class", "Main.magma");
		Path path = CompileTask.getMainClass(SOURCE, properties);
		assertEquals(MAIN, path);
	}

	@Test
	void mainExtinct() {
		Properties properties = new Properties();
		properties.setProperty("Main-Class", "Main.magma");
		assertThrows(CompileException.class, () -> CompileTask.getMainClass(SOURCE, properties));
	}

	@Test
	void noMain() {
		assertThrows(CompileException.class, () -> CompileTask.getMainClass(null, new Properties()));
	}

	@Test
	void outputMain() throws IOException {
		Path test = CompileTask.outputMain(CompileTask.TARGET, "test");
		assertEquals(TARGET.resolve("out.c"), test);
		assertTrue(Files.exists(test));
		assertEquals("test", Files.readString(test));
	}

	@Test
	void performImpl() {
		Map<Throwable, String> map = new HashMap<>();
		Recorder recorder = new ConsumableRecorder(Collections.singletonMap(RecordLevel.WARNING, map::put));
		Properties properties = new Properties();
		Binding<Properties> binding = new InlineBinding<>(properties);
		Task task = new CompileTask(recorder, binding);
		task.perform();
		assertTrue(map.isEmpty());
	}

	@AfterEach
	void tearDown() throws IOException {
		cleanup();
	}
}