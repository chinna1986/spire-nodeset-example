package com.spotify.spire.model.nodeset;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * A context for the built-in operators and functions.
**/
public class BuiltinNodeSetContext implements NodeSetContext {
	public static final String BUILTIN_NAMESPACE = "__builtin__";

	@Override
	public Object evaluate(String[] qidentifier, Object[] args) {
		if (qidentifier.length != 2)
			return null;

		if (!BUILTIN_NAMESPACE.equals(qidentifier[0]))
			return null;

		if (args.length != 2)
			throw new IllegalArgumentException("Operators need exactly two arguments");

		String op = qidentifier[1];

		if ("\\".equals(op)) {
			Set<Object> ret = toSet(args[0]);

			ret.removeAll(toSet(args[1]));

			return ret;
		} else if ("∪".equals(op)) {
			Set<Object> ret = toSet(args[0]);

			ret.addAll(toSet(args[1]));

			return ret;
		} else if ("⊖".equals(op)) {
			Set<Object> a = toSet(args[0]);
			Set<Object> ret = toSet(args[1]);

			a.removeAll(toSet(args[1]));   // args0 - args1
			ret.removeAll(toSet(args[0])); // args1 - args0
			ret.addAll(a);                 // (args1 - args0) | (args0 - args1)

			return ret;
		} else if ("∩".equals(op)) {
			Set<Object> ret = toSet(args[0]);

			ret.retainAll(toSet(args[1]));

			return ret;
		}

		throw new IllegalArgumentException("Unknown operator: " + op);
	}

	/**
	 * Try to convert an object to a set of objects.
	 *
	 * @throw IllegalArgumentException if the object cannot be converted to a set.
	**/
	private Set<Object> toSet(Object o) {
		if (o instanceof Collection) {
			return new HashSet<Object>((Collection<?>) o);
		}

		throw new IllegalArgumentException("Cannot convert " + o + " to a set");
	}
}
