/**
 * 
 */
package util;

import static metagenerics.transform.parse.MetaJavaLexer.tokenizeFile;

import java.io.IOException;
import java.util.Comparator;


public final class FileByTokenComparator implements Comparator<String> {
	
	CommonTokenListComparator comparator = new CommonTokenListComparator();
	
	public int compare(String expected, String actual) {
		try {
			return comparator.compare(
					tokenizeFile(expected), tokenizeFile(actual));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public String getError() {
		return comparator.getError();
	}
}