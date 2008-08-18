package util;

import java.io.File;
import java.io.IOException;

import util.exception.NotAFileOrFolderException;

public class FolderUtils {
	static public void ensureFolderExist(String folder) throws IOException {
		File file = new File(folder);
		if (!file.isDirectory() && !file.mkdirs())
			throw new IOException("Unanable to create folder " + folder);
	}

	static public boolean isFolder(String folder) {
		return new File(folder).isDirectory();
	}

	static public void folderDelete(String folder)
			throws NotAFileOrFolderException {
		File file = new File(folder);
		if (file.isFile())
			file.delete();
		else if (file.isDirectory())
			for (File element : file.listFiles()) {
				folderDelete(element.getPath());
				file.delete();
			}
		else
			throw new NotAFileOrFolderException(folder);
	}

}
