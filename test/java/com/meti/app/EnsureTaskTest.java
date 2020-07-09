package com.meti.app;

import com.meti.log.LoggedRecorder;
import com.meti.log.Recorder;
import com.meti.task.Task;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EnsureTaskTest {
	public static final Path PATH = Paths.get(".", "test", "test");

	@AfterAll
	static void after() throws IOException {
		Files.deleteIfExists(PATH);
	}

	@BeforeAll
	static void before() throws IOException {
		Files.deleteIfExists(PATH);
	}

	@Test
	void performImpl() {
		Recorder recorder = new LoggedRecorder();
		Task task = new EnsureTask(recorder, PATH);
		assertFalse(task.perform());
		assertTrue(Files.exists(PATH));
	}
}