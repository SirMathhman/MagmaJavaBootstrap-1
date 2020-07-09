package com.meti.compile;

import com.meti.compile.node.MagmaNodeSource;
import com.meti.compile.node.Node;
import com.meti.compile.node.NodeSource;
import com.meti.compile.node.StringNode;
import com.meti.compile.type.MagmaTypeSource;
import com.meti.compile.type.Type;
import com.meti.compile.type.TypeSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class MagmaCompiler implements Compiler {
	public static final Compiler INSTANCE = new MagmaCompiler();
	private final NodeSource nodeSource = new MagmaNodeSource();
	private final TypeSource typeSource = new MagmaTypeSource();

	private MagmaCompiler() {
	}

	@Override
	public String compile(String value) {
		Node node = nodeSource.parse(new StringNode(value));
		Node toReturn = parse(node);
		return toReturn.toString();
	}

	private Node parse(Node node) {
		if (node.getValue().isPresent()) {
			return nodeSource.parse(node);
		} else if (node.isParsed()) {
			return node;
		} else {
			Map<String, Type> newFields = parseFields(node);
			List<Node> newChildren = parseChildren(node);
			return node.copy(newFields, newChildren);
		}
	}

	private List<Node> parseChildren(Node node) {
		return node.getChildren()
				.stream().map(this::parse)
				.collect(Collectors.toList());
	}

	private Map<String, Type> parseFields(Node node) {
		Map<String, Type> oldFields = node.getFields();
		Map<String, Type> newFields = new HashMap<>();
		for (Map.Entry<String, Type> entry : oldFields.entrySet()) {
			Type oldValue = entry.getValue();
			Type newValue = typeSource.resolve(oldValue);
			String key = entry.getKey();
			newFields.put(key, newValue);
		}
		return newFields;
	}
}
