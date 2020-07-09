package com.meti.compile.resolve;

import com.meti.compile.type.PrimitiveType;
import com.meti.compile.type.TypeStage;
import com.meti.compile.type.Type;

public class UnknownTypeStage implements TypeStage {
	@Override
	public boolean canAccept(Type type) {
		return PrimitiveType.UNKNOWN == type;
	}

	@Override
	public Type accept(Type type) {
		return type;
	}
}
