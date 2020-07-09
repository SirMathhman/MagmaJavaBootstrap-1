package com.meti.log;

import java.util.Collection;
import java.util.logging.LogRecord;

public interface Collectable<T> {
	Collection<T> get();
}
