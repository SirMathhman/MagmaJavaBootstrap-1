package com.meti.log;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ConsumableRecorderTest {
	private static final Exception DUMMY_EXCEPTION = new Exception();

	@Test
	void notSet() {
		Recorder recorder = new ConsumableRecorder(Collections.singletonMap(RecordLevel.WARNING, null));
		assertThrows(IllegalArgumentException.class, () -> recorder.severe(DUMMY_EXCEPTION, "test"));
	}

	@Test
	void severe() {
		Map<Throwable, String> map = new HashMap<>();
		Recorder recorder = new ConsumableRecorder(Collections.singletonMap(RecordLevel.SEVERE, map::put));
		recorder.severe(DUMMY_EXCEPTION, "test");
		assertEquals(1, map.size());
		assertTrue(map.containsKey(DUMMY_EXCEPTION));
		assertEquals("test", map.get(DUMMY_EXCEPTION));
	}
}