package com.meti.app;

import com.meti.app.properties.PropertiesEnsureTask;
import com.meti.app.properties.PropertiesLoadTask;
import com.meti.app.properties.PropertiesStoreTask;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MagmaTaskSupplierTest {

	@Test
	void getTasks() {
		TaskSupplier supplier = new MagmaTaskSupplier();
		List<? extends Class<?>> taskClasses = supplier.getTasks().stream()
				.map(Object::getClass)
				.collect(Collectors.toList());
		assertEquals(List.of(
				EnsureTask.class,
				PropertiesLoadTask.class,
				PropertiesEnsureTask.class,
				PropertiesEnsureTask.class,
				CompileTask.class,
				PropertiesStoreTask.class
		), taskClasses);
	}
}