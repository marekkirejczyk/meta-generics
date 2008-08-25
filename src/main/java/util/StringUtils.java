package util;

import java.util.List;

import org.antlr.runtime.CommonToken;

public class StringUtils {

	public static String capitalize(String s) {
		if (s == null || s.length() < 1)
			return s;
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	public static String decapitalize(String s) {
		if (s == null || s.length() < 1)
			return s;
		return s.substring(0, 1).toLowerCase() + s.substring(1);
	}

	public static boolean isWhite(String str) {
		for (int i = 0; i < str.length(); i++)
			if (!isWhite(str.charAt(i)))
				return false;
		return true;
	}

	public static boolean isWhite(char c) {
		return c == ' ' || c == '\n' || c == '\t';
	}

	public static boolean isWhite(CommonToken token) {
		String text = token.getText();
		return text.equals(" ") || text.equals("\n") || text.equals("\t");
	}

	public static String formatCollection(List<String> listOfString,
			String separator) {
		StringBuilder result = new StringBuilder();
		boolean first = true;
		for (String token : listOfString) {
			if (first)
				first = false;
			else
				result.append(separator);
			result.append(token);
		}
		return result.toString();
	}

	static public String getSuffix(String prefix, String string) {
		int i = 0;
		while (i < prefix.length() && i < string.length()
				&& prefix.charAt(i) == string.charAt(i))
			i++;
		return string.substring(i);
	}

	static public String escapeCharacters(String text) {
		StringBuilder result = new StringBuilder();

		for (int i = 0; i < text.length(); i++) {
			boolean previousIsSlash = (i != 0) && (text.charAt(i - 1) == '\\');
			if (text.charAt(i) == '"' && !previousIsSlash)
				result.append("\\\"");
			else
				result.append(text.charAt(i));
		}
		return result.toString();
	}

}
