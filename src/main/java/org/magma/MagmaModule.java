package org.magma;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Binder;
import com.google.inject.Module;
import org.magma.util.NameProvider;
import org.magma.util.Scope;
import org.magma.util.ScopedProvider;
import org.magma.util.TreeScope;

class MagmaModule implements Module {
	private final ObjectMapper mapper = new ObjectMapper();
	private final Scope scope = new TreeScope();
	private final NameProvider provider = new ScopedProvider(scope);

	@Override
	public void configure(Binder binder) {
		binder.bind(ObjectMapper.class).toInstance(mapper);
		binder.bind(Scope.class).toInstance(scope);
		binder.bind(NameProvider.class).toInstance(provider);
	}
}
