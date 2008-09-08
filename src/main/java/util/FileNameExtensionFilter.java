package util;

import java.io.File;
import java.io.FilenameFilter;

public class FileNameExtensionFilter implements FilenameFilter {

	String extension;

	public FileNameExtensionFilter(String extension) {
		this.extension = "." + extension;
	}

	public boolean accept(File folder, String filename) {
		return filename.endsWith(extension);
	}

}
