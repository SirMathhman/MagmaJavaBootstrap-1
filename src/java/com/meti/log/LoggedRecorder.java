package com.meti.log;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggedRecorder extends AbstractRecorder {
	private final Logger logger;

	public LoggedRecorder() {
		this(Logger.getAnonymousLogger());
	}

	public LoggedRecorder(Logger logger) {
		this.logger = logger;
	}

	@Override
	public void severe(Throwable e, String message) {
		logger.log(Level.SEVERE, message, e);
	}

	@Override
	public void warning(Throwable e, String message) {
		logger.log(Level.WARNING, message, e);
	}
}