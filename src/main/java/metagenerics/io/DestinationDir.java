package metagenerics.io;

import java.io.File;

public class DestinationDir {
	File dir;

	public DestinationDir(File dir) {
		if (dir.isFile())
			throw new RuntimeException("Destination dir is a file");
		if (!dir.isDirectory())
			dir.mkdirs();

		this.dir = dir;
	}

	public File getDir() {
		return dir;
	}

}
