package com.meti.compile.load;

import com.meti.MagmaException;

public class LoadException extends MagmaException  {
	public LoadException(String message) {
		super(message);
	}

	public LoadException(String message, Throwable cause) {
		super(message, cause);
	}
}
