package com.meti.log;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class LoggedRecorderTest {
	public static final Exception TEST_EXCEPTION = new Exception();

	@Test
	void severeException() {
		CollectableHandler handler = new ListHandler();
		Recorder recorder = buildRecorder(handler);
		recorder.severe(TEST_EXCEPTION, "test");
		LogRecord record = getFirstResult(handler);
		assertSame(TEST_EXCEPTION, record.getThrown());
	}

	private static Recorder buildRecorder(Handler handler) {
		Logger logger = Logger.getAnonymousLogger();
		logger.setUseParentHandlers(false);
		logger.addHandler(handler);
		return new LoggedRecorder(logger);
	}

	private static LogRecord getFirstResult(Collectable<? extends LogRecord> handler) {
		Collection<? extends LogRecord> collection = handler.get();
		List<? extends LogRecord> asList = new ArrayList<>(collection);
		return asList.get(0);
	}

	@Test
	void severeExecutableException() {
		CollectableHandler handler = new ListHandler();
		Recorder recorder = buildRecorder(handler);
		assertTrue(recorder.severe(LoggedRecorderTest::throwTest, "test"));
		LogRecord record = getFirstResult(handler);
		assertSame(TEST_EXCEPTION, record.getThrown());
	}

	private static void throwTest() throws Exception {
		throw TEST_EXCEPTION;
	}

	@Test
	void severeExecutableMessage() {
		CollectableHandler handler = new ListHandler();
		Recorder recorder = buildRecorder(handler);
		assertTrue(recorder.severe(LoggedRecorderTest::throwTest, "test"));
		LogRecord record = getFirstResult(handler);
		assertSame("test", record.getMessage());
	}

	@Test
	void warningExecutableMessage() {
		CollectableHandler handler = new ListHandler();
		Recorder recorder = buildRecorder(handler);
		assertTrue(recorder.warning(LoggedRecorderTest::throwTest, "test"));
		LogRecord record = getFirstResult(handler);
		assertSame("test", record.getMessage());
	}

	@Test
	void severeExecutableSize() {
		CollectableHandler handler = new ListHandler();
		Recorder recorder = buildRecorder(handler);
		assertTrue(recorder.severe(LoggedRecorderTest::throwTest, "test"));
		assertEquals(1, handler.get().size());
	}

	@Test
	void severeMessage() {
		CollectableHandler handler = new ListHandler();
		Recorder recorder = buildRecorder(handler);
		recorder.severe(TEST_EXCEPTION, "test");
		LogRecord record = getFirstResult(handler);
		assertSame("test", record.getMessage());
	}

	@Test
	void severeSize() {
		CollectableHandler handler = new ListHandler();
		Recorder recorder = buildRecorder(handler);
		recorder.severe(TEST_EXCEPTION, "test");
		assertEquals(1, handler.get().size());
	}

	@Test
	void warning() {
		CollectableHandler handler = new ListHandler();
		Recorder recorder = buildRecorder(handler);
		recorder.warning(TEST_EXCEPTION, "test");
		assertEquals(1, handler.get().size());
	}
}