package util;

import java.io.File;

public class PathUtils {
	
	static public boolean isPrefix(String what, String prefix) {
		if (what.length() < prefix.length())
			return false;
		int i = 0;
		while (i < prefix.length() && what.charAt(i) == prefix.charAt(i))
			i++;
		return i == prefix.length();
	}

	static public String pathDiff(String path, String prefix) {
		Assert.assertTrue(isPrefix(path, prefix));
		return path.substring(prefix.length(), path.length());
	}

	static public String stripFileName(String path) {
		int end = path.lastIndexOf(File.separatorChar);
		return path.substring(0, end);
	}

	public static String sourceDirectoryAndClassToJavaFileName(String sourceDirectory,
			String klass) {
		return sourceDirectory + File.separator
				+ klass.replace(".", File.separator) + ".java";
	}

	public static String sourceDirectoryAndClassToClassFileName(String sourceDirectory,
			String klass) {
		return sourceDirectory + File.separator
				+ klass.replace(".", File.separator) + ".class";
	}

}
