package com.spotify.spire.model.nodeset;

import lombok.Data;

/**
 * A context trying multiple subcontexts in sequence.
**/
@Data
public class SequenceNodeSetContext implements NodeSetContext {
	private final NodeSetContext[] contexts;

	@Override
	public Object evaluate(String[] qidentifier, Object[] args) {
		for (NodeSetContext ctx : this.contexts) {
			Object ret = ctx.evaluate(qidentifier, args);

			if (ret != null)
				return ret;
		}

		return null;
	}
}
