package com.meti.app.properties;

import com.meti.task.Task;
import com.meti.util.Binding;

import java.util.Properties;

public class PropertiesEnsureTask implements Task {
	private final String key;
	private final Binding<? extends Properties> properties;
	private final String value;

	public PropertiesEnsureTask(Binding<? extends Properties> properties, String key, String source) {
		this.properties = properties;
		this.key = key;
		this.value = source;
	}

	@Override
	public boolean perform() {
		if (!properties.get().containsKey(key)) {
			properties.get().setProperty(key, value);
		}
		return false;
	}
}
