package com.meti.compile.process;

import com.meti.compile.node.Token;
import com.meti.compile.node.TokenGroup;

public interface Preprocessor {
	boolean canPreprocess(TokenGroup group);

	Token preprocess(Token token);
}
