package com.meti;

public interface ParseRule {
	boolean canQualify(String content);

	Node parse(String content, Compiler compiler);
}
