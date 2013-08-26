package com.spotify.spire.model.nodeset;

public class Utils {
	/**
	 * Join strings with the specified delimiter in-between.
	**/
	public static String joinStrings(Iterable<String> parts, String delim) {
		StringBuilder sb = new StringBuilder();
		boolean first = true;

		for (String part : parts) {
			if (first) first = false;
			else sb.append(delim);
			sb.append(part);
		}

		return sb.toString();
	}

	private Utils() {
	}
}
