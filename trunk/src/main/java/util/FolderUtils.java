package util;

import java.io.File;
import java.io.IOException;

public class FolderUtils {
	static public void ensureFolderExist(String folder) throws IOException {
		File file = new File(folder);
		if (!file.isDirectory() && !file.mkdir())
			throw new IOException("Unanable to create folder " + folder);
	}
}
