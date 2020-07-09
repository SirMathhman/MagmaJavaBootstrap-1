package com.meti.compile.type;

import com.meti.MagmaException;
import com.meti.compile.resolve.IntTypeStage;
import com.meti.compile.resolve.UnknownTypeStage;

import java.util.Collection;
import java.util.List;

public class MagmaTypeSource implements TypeSource {
	private final Collection<TypeStage> typeStages = List.of(
			new IntTypeStage(),
			new UnknownTypeStage()
	);

	@Override
	public Type resolve(Type type) {
		return typeStages.stream()
				.filter(typeStage -> typeStage.canAccept(type))
				.map(typeStage -> typeStage.accept(type))
				.findFirst()
				.orElseThrow(() -> new MagmaException("Failed to accept: " + type));
	}
}
