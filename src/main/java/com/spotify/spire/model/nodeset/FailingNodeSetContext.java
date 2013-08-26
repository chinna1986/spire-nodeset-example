package com.spotify.spire.model.nodeset;

/**
 * A context failing any and all evaluations.
 *
 * Useful as a last-resort context.
**/
public class FailingNodeSetContext implements NodeSetContext {
	@Override
	public Object evaluate(String[] qidentifier, Object[] args) {
		StringBuilder sb = new StringBuilder();
		boolean first = true;

		for (String id : qidentifier) {
			if (first) first = false;
			else sb.append(".");

			sb.append(id);
		}

		throw new IllegalArgumentException("No match for " + sb);
	}
}
