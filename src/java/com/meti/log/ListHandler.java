package com.meti.log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.logging.LogRecord;

class ListHandler extends CollectableHandler {
	private final Collection<LogRecord> records;

	ListHandler() {
		this(Collections.emptyList());
	}

	ListHandler(Collection<LogRecord> records) {
		this.records = new ArrayList<>(records);
	}

	@Override
	public Collection<LogRecord> get() {
		return Collections.unmodifiableCollection(records);
	}

	@Override
	public void publish(LogRecord record) {
		records.add(record);
	}
}
