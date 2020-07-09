package com.meti.compile.load;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

public interface ClassLoader {
	String load(String... className) throws IOException;
}
