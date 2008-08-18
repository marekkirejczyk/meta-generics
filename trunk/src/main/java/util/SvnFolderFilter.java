/**
 * 
 */
package util;


public final class SvnFolderFilter implements Filter<String> {
	public boolean accept(String t) {
		return !t.equals(".svn");
	}
}