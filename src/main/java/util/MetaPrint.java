package util;

import java.io.PrintStream;

public class MetaPrint {

	static public String trimNextLine(String text) {
		if (text.length() > 0)
			if (text.charAt(text.length() - 1) == Character.LINE_SEPARATOR)
				text = text.substring(0, text.length() - 1) + "\\" + "n";
		return text;
	}

	static public String escapeCharacters(String text) {
		String result = "";
		for (int i = 0; i  < text.length(); i++)
			result += (text.charAt(i) == '\"') ? "\\" + "\"" : text.charAt(i);
		return result;
	}
	
	static public void metaPrintQuotedLines(PrintStream ps, String text) {
		text = escapeCharacters(text);
		String[] lines = ("\t" + text).split("\n");
		for (String line : lines)
			metaPrintQuoted(ps, trimNextLine(line));

	}

	static public String endl() {
		return "\\" + "n";
	}
	
	static public String quotedEndl() {
		return "+ \"\\" + "n\"";
	}
	
	
	static public void metaPrintQuoted(PrintStream ps, String text) {
		ps.print("\t\tps.print(\"");
		ps.print(text);
		ps.print("\");\n");
	}

	static public void metaPrintln(PrintStream ps, String ... strings) {
		ps.print("\t\tps.print(");
		boolean first = true;
		for (String str: strings) {
			if (!first)
				ps.print(" + ");
			ps.print(str);
			first = false;
		}
		ps.print(quotedEndl() + ");\n");
	}

	static public void metaPrint(PrintStream ps, String text) {
		ps.print("\t\tps.print(");
		ps.print(text);
		ps.print(");\n");
	}

	static public void print(PrintStream ps, String text) {
		ps.print(text);
	}
}
