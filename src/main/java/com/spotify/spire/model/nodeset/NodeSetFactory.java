package com.spotify.spire.model.nodeset;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import java_cup.runtime.Symbol;

/**
 * Factory function for node sets.
 *
 * This allows various operation on node sets and its textual representation.
**/
public class NodeSetFactory {
	/**
	 * Evaluate a node set expression syntax tree.
	 *
	 * @param expr the parsed expression.
	 * @param context the context to evaluate function calls.
	 * @return whatever the expression evaluates to.
	 * @throw IllegalArgumentException if a node type is unknown.
	**/
	public Object evaluate(NodeSetTree.Expression expr, NodeSetContext context) {
		if (expr instanceof NodeSetTree.CallExpression) {
			NodeSetTree.CallExpression call = (NodeSetTree.CallExpression) expr;
			Object[] args = new Object[call.getArgs().size()];

			for (int i = 0; i < args.length; ++i)
				args[i] = evaluate(call.getArgs().get(i), context);

			return context.evaluate(call.getQidentifier().toArray(new String[0]), args);
		} else if (expr instanceof NodeSetTree.ListExpression) {
			NodeSetTree.ListExpression list = (NodeSetTree.ListExpression) expr;
			List<Object> ret = new ArrayList<>(list.getValues().size());

			for (NodeSetTree.Expression e : list.getValues())
				ret.add(evaluate(e, context));

			return ret;
		} else if (expr instanceof NodeSetTree.LiteralExpression) {
			return ((NodeSetTree.LiteralExpression) expr).getValue();
		}

		throw new IllegalArgumentException("Unknown expression type: " + expr.getClass().getName());
	}

	/**
	 * Evaluate a node set expression syntax tree as a node set.
	 *
	 * This is just a type casting wrapper around evaluate().
	**/
	@SuppressWarnings("unchecked")
	public Collection<String> evaluateNodeSet(NodeSetTree.Expression expr, NodeSetContext context) {
		return (Collection<String>) evaluate(expr, context);
	}

	/**
	 * Check if the string is a valid node set expression.
	 *
	 * This just checks syntax, not types and such.
	**/
	public boolean isValidNodeSetExpression(String expr) {
		try {
			parseExpression(expr);
		} catch (Exception ex) {
			return false;
		}

		return true;
	}

	/**
	 * Parse the given string as a node set expression.
	 *
	 * @return a syntax tree for the expression.
	 * @throw Exception if parsing fails (yepp, CUP forces us to declare Exception thrown.)
	**/
	public NodeSetTree.Expression parseExpression(String expr) throws Exception {
		NodeSetScanner scanner = new NodeSetScanner(new StringReader(expr));
		NodeSetParser parser = new NodeSetParser(scanner);
		Symbol sym = parser.parse();

		return (NodeSetTree.Expression) sym.value;
	}
}
