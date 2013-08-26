package com.spotify.spire.model.nodeset;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class NodeSetMain {
	public static void main(String[] args) throws Exception {
		NodeSetFactory nsf = new NodeSetFactory();
		boolean first = true;

		for (String arg : args) {
			NodeSetTree.Expression expr = nsf.parseExpression(arg);
			Object result = nsf.evaluate(
				expr,
				new SequenceNodeSetContext(new NodeSetContext[] {
					new BuiltinNodeSetContext(),  // This handles the built-in operators.
					new FileSystemContext(),      // See below.
					new FailingNodeSetContext()   // Ensure we fail all invalid calls.
				})
			);

			if (first) first = false;
			else System.out.println("---");

			System.out.println("Input: " + arg);
			System.out.println("Tree : " + expr);
			System.out.println("Value: " + result);
		}
	}

	/**
	 * A test-context for playing around with the node sets.
	 *
	 *   fs.ls(path)    will return a set of file names at the given path.
	 *   fs.ls.current  will return a set of file names in the current directory.
	**/
	private static class FileSystemContext implements NodeSetContext {
		@Override
		public Object evaluate(String[] qidentifier, Object[] args) {
			if (qidentifier.length < 2)
				return null;

			if (!"fs".equals(qidentifier[0]))
				return null;

			if (qidentifier.length == 3 && "ls".equals(qidentifier[1]) && "current".equals(qidentifier[2]))
				return ls(qidentifier, new Object[] { "." });

			if (qidentifier.length == 2 && "ls".equals(qidentifier[1]))
				return ls(qidentifier, args);

			return null;
		}

		private Set<String> ls(String[] qidentifier, Object[] args) {
			if (args.length != 1)
				throw new IllegalArgumentException("invalid number of arguments for 'fs.ls': " + args.length);

			return new HashSet<String>(Arrays.asList(new File((String) args[0]).list()));
		}
	}
}
