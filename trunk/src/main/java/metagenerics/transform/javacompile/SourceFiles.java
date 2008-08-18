package metagenerics.transform.javacompile;

public class SourceFiles {
	static public final String SOURCE_EXTENSION = ".java";

	static public boolean isSourceFileName(String fileName) {
		return fileName.endsWith(SOURCE_EXTENSION);
	}
}
