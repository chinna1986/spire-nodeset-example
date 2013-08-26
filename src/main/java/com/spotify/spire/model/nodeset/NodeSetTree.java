package com.spotify.spire.model.nodeset;

import java.util.Arrays;
import java.util.List;

import lombok.Data;

/**
 * AST node definitions for node sets.
 *
 * This class is just a container for other interfaces and classes.
**/
public class NodeSetTree {
	/**
	 * Base interface for all expression nodes.
	**/
	public interface Expression {
	}

	/**
	 * An expression for a call (with zero or more arguments.)
	 *
	 * Note that even binary operations use this node type.
	**/
	@Data
	public static class CallExpression implements Expression {
		private final List<String> qidentifier;
		private final List<Expression> args;

		public CallExpression(List<String> qidentifier, List<Expression> args) {
			this.qidentifier = qidentifier;
			this.args = args;
		}

		public CallExpression(List<String> qidentifier, Expression... args) {
			this(qidentifier, Arrays.asList(args));
		}
	}

	/**
	 * An expression for a single list value.
	**/
	@Data
	public static class ListExpression implements Expression {
		private final List<Expression> values;
	}

	/**
	 * An expression for a single literal value.
	 *
	 * Literals supported are Boolean, Double, Integer, and String.
	**/
	@Data
	public static class LiteralExpression implements Expression {
		private final Object value;
	}
}
