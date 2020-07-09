package com.meti.log;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class ConsumableRecorder extends AbstractRecorder {
	private final Map<RecordLevel, BiConsumer<Throwable, String>> consumers;

	public ConsumableRecorder(Map<RecordLevel, BiConsumer<Throwable, String>> consumers) {
		this.consumers = new EnumMap<>(consumers);
	}

	@Override
	public void severe(Throwable e, String message) {
		getConsumer(RecordLevel.SEVERE).accept(e, message);
	}

	private BiConsumer<Throwable, String> getConsumer(RecordLevel level) {
		if (consumers.containsKey(level)) {
			return consumers.get(level);
		} else {
			throw new IllegalArgumentException(level + " has not been set.");
		}
	}

	@Override
	public void warning(Throwable e, String message) {

	}
}
