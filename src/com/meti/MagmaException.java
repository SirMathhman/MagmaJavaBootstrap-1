package com.meti;

public class MagmaException extends RuntimeException {
	public MagmaException(String message, Throwable cause) {
		super(message, cause);
	}

	public MagmaException(String message) {
		super(message);
	}
}
