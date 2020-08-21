package com.meti;

import java.util.Optional;

public interface ParseRule {
	Optional<Node> parse(String content, Compiler compiler);
}
