package com.meti;

public class CompileException extends MagmaException {
	private static final long serialVersionUID = 305546052447562635L;

	public CompileException(String message) {
		super(message);
	}

	public CompileException(String message, Throwable cause) {
		super(message, cause);
	}
}
