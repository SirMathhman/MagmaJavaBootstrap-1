package com.meti.compile.resolve;

import com.meti.compile.type.PrimitiveType;
import com.meti.compile.type.TypeStage;
import com.meti.compile.type.Type;

public class IntTypeStage implements TypeStage {
	@Override
	public boolean canAccept(Type type) {
		return type.getValue()
				.filter("Int"::equals)
				.isPresent();
	}

	@Override
	public Type accept(Type type) {
		return PrimitiveType.INT;
	}
}
