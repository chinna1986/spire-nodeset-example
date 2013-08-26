package com.spotify.spire.model.nodeset;

/**
 * A context used when evaluating node set expressions.
**/
public interface NodeSetContext {
	/**
	 * Evaluate the specified identifier.
	**/
	public Object evaluate(String[] qidentifier, Object[] args);
}
