package com.meti;

public interface Config {
	void load() throws LoggedException;

	void store() throws LoggedException;
}
