package org.magma.util;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TreeScope implements Scope {
	private final TreeNode root = new TreeNode("root", null, null);
	private TreeNode current = root;

	@Override
	public void define(String name, JsonNode type) {
		current.define(name, type);
	}

	@Override
	public void enter(String name) {
		if (current.isDefined(name)) {
			current = current.get(name);
		} else {
			throw new IllegalStateException(name + " has to be defined first.");
		}
	}

	@Override
	public void exit() {
		current.getParent().ifPresent(parent -> current = parent);
	}

	@Override
	public boolean isDefined(String name) {
		return current.isDefined(name);
	}

	private static final class TreeNode {
		private final Map<String, TreeNode> children = new HashMap<>();
		private final String name;
		private final TreeNode parent;
		private final JsonNode type;

		private TreeNode(String name, JsonNode type, TreeNode parent) {
			this.name = name;
			this.type = type;
			this.parent = parent;
		}

		public void define(String name, JsonNode type) {
			TreeNode node = new TreeNode(name, type, this);
			children.put(name, node);
		}

		public TreeNode get(String name) {
			return children.get(name);
		}

		public String getName() {
			return name;
		}

		public Optional<TreeNode> getParent() {
			return Optional.ofNullable(parent);
		}

		public JsonNode getType() {
			return type;
		}

		public boolean isDefined(String name) {
			return children.containsKey(name) || (null != parent && parent.isDefined(name));
		}
	}
}
