package com.meti;

public class MagmaException extends RuntimeException {
	private static final long serialVersionUID = 5268501544257674705L;

	public MagmaException(String message) {
		super(message);
	}

	public MagmaException(String message, Throwable cause) {
		super(message, cause);
	}
}
