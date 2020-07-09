package com.meti.log;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public abstract class CollectableHandler extends Handler implements Collectable<LogRecord> {
	@Override
	public void flush() {
	}

	@Override
	public void close() {
	}
}
