package com.meti.compile.process;

import com.meti.compile.node.Token;

public interface ProcessStage {
	Token process(Token token);
}
