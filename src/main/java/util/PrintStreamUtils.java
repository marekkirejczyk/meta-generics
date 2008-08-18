package util;

import java.io.PrintStream;

public class PrintStreamUtils {
	static public void printIndents(PrintStream ps, int n) {
		for (int i = 0; i < n; i++)
			ps.print("  ");
	}
	
	static public void printWithIndents(PrintStream ps, int n, String msg) {
		printIndents(ps, n);
		ps.print(msg);
	}
}
