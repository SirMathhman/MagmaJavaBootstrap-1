package com.meti.app;

import com.meti.task.Task;
import com.meti.util.Binding;
import com.meti.util.InlineBinding;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {
	private static final String[] EMPTY_ARGS = new String[0];
	private static final Binding<Boolean> binding = new InlineBinding<>(false);
	private static final Task TASK = MainTest::updateBinding;

	private static boolean updateBinding() {
		binding.set(true);
		return false;
	}

	@Test
	void main() {
		TaskSupplier temp = Main.supplier;
		Main.supplier = () -> Collections.singleton(TASK);
		Main.main(EMPTY_ARGS);
		Main.supplier = temp;
		assertTrue(binding.get());
	}
}