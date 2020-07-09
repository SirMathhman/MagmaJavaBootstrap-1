package com.meti.log;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Set;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import static java.util.Collections.singleton;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class ListHandlerTest {

	@Test
	void close() {
		Handler handler = new ListHandler();
		assertDoesNotThrow(handler::close);
	}

	@Test
	void flush() {
		Handler handler = new ListHandler();
		assertDoesNotThrow(handler::flush);
	}

	@Test
	void get() {
		LogRecord record = Mockito.mock(LogRecord.class);
		Set<LogRecord> collection = singleton(record);
		Collectable<LogRecord> handler = new ListHandler(collection);
		assertIterableEquals(collection, handler.get());
	}

	@Test
	void publish() {
		CollectableHandler handler = new ListHandler();
		LogRecord record = Mockito.mock(LogRecord.class);
		handler.publish(record);
		assertIterableEquals(singleton(record), handler.get());
	}
}