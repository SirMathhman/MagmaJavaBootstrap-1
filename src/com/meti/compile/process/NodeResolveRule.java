package com.meti.compile.process;

import com.meti.compile.node.Dependents;
import com.meti.compile.type.Type;

public interface NodeResolveRule {
	Dependents resolve(Dependents dependents, Type returnType, Resolver resolver);
}
