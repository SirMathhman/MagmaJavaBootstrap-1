package com.meti.app.properties;

import com.meti.task.Task;
import com.meti.util.Binding;
import com.meti.util.InlineBinding;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PropertiesEnsureTaskTest {

	@Test
	void performExtant() {
		Properties properties = new Properties();
		properties.setProperty("test", "value");
		Binding<Properties> binding = new InlineBinding<>(properties);
		Task task = new PropertiesEnsureTask(binding, "test", "value0");
		task.perform();
		assertEquals(1, properties.size());
		assertEquals("value", properties.getProperty("test"));
	}

	@Test
	void performExtinct() {
		Properties properties = new Properties();
		Binding<Properties> binding = new InlineBinding<>(properties);
		Task task = new PropertiesEnsureTask(binding, "test", "value0");
		task.perform();
		assertEquals(1, properties.size());
		assertEquals("value0", properties.getProperty("test"));
	}
}