package com.meti.compile.process;

import com.meti.compile.node.Token;
import com.meti.compile.node.TokenGroup;

public interface Processor {
	boolean canProcess(TokenGroup group);

	Token process(Token token);
}
