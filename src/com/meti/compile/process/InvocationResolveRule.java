package com.meti.compile.process;

import com.meti.compile.node.Dependents;
import com.meti.compile.node.Node;
import com.meti.compile.type.Type;
import com.meti.compile.type.TypeGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class InvocationResolveRule implements NodeResolveRule {
	@Override
	public Dependents resolve(Dependents dependents, Type returnType, Resolver resolver) {
		List<Node> children = dependents.streamChildren().collect(Collectors.toList());
		Node caller = children.get(0);
		List<Node> arguments = children.subList(1, children.size());
		List<Node> newChildren = createNewChildren(caller, arguments, returnType, resolver);
		return dependents.copyChildren(newChildren);
	}

	public List<Node> createNewChildren(Node caller, List<Node> arguments, Type returnType, Resolver resolver) {
		Type expectedType = findValidType(caller, arguments, returnType, resolver);
		Node newCaller = composeNewCaller(caller, expectedType, resolver);
		List<Node> newArguments = composeNewArguments(arguments, expectedType, resolver);
		List<Node> newChildren = new ArrayList<>();
		newChildren.add(newCaller);
		newChildren.addAll(newArguments);
		return newChildren;
	}

	public static Type findValidType(Node caller, Collection<Node> arguments, Type returnType, Resolver resolver) {
		List<List<Type>> actualPermutations = searchArguments(arguments, resolver);
		List<Type> callerTypes = resolver.search(caller);
		return callerTypes.stream()
				.filter(type -> type.applyToGroup(TypeGroup.Function::matches))
				.filter(functionType -> matchReturn(returnType, functionType))
				.filter(functionType -> matchArgumentsFromType(actualPermutations, functionType))
				.findFirst()
				.orElseThrow(() -> {
					String message = "No valid types were found for caller %s.".formatted(caller);
					return new ProcessException(message);
				});
	}

	public static Node composeNewCaller(Node caller, Type expectedFunctionType, Resolver resolver) {
		return resolver.force(caller, expectedFunctionType);
	}

	public static List<Node> composeNewArguments(List<Node> arguments, Type expectedFunctionType, Resolver resolver) {
		List<Type> argumentTypes = expectedFunctionType.streamChildren().collect(Collectors.toList());
		List<Type> actualArgumentTypes = argumentTypes.subList(1, argumentTypes.size());
		List<Node> newArguments = new ArrayList<>();
		for (int i = 0; i < actualArgumentTypes.size(); i++) {
			newArguments.add(resolver.force(arguments.get(i), actualArgumentTypes.get(i)));
		}
		return newArguments;
	}

	public static List<List<Type>> searchArguments(Collection<Node> arguments, Resolver resolver) {
		return arguments.stream()
				.map(resolver::search)
				.collect(Collectors.toList());
	}

	public static boolean matchReturn(Type expectedType, Type type) {
		return type.streamChildren()
				.findFirst()
				.filter(expectedType::equals)
				.isPresent();
	}

	public static boolean matchArgumentsFromType(List<List<Type>> actualPermutations, Type functionType) {
		List<Type> functionChildren = functionType.streamChildren().collect(Collectors.toList());
		List<Type> functionArguments = functionChildren.subList(1, functionChildren.size());
		return matchArguments(actualPermutations, functionArguments);
	}

	public static boolean matchArguments(List<List<Type>> argumentPermutations, List<Type> expected) {
		if (argumentPermutations.isEmpty()) return true;
		for (int i = 0; i < expected.size(); i++) {
			List<Type> permutation = argumentPermutations.get(i);
			if(!permutation.contains(expected.get(i))) return false;
		}
		return true;
	}
}