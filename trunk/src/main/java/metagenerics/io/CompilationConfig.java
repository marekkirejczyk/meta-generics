package metagenerics.io;

import java.io.File;

public class CompilationConfig {
	final static String META_SRC = "metasrc";
	
	DestinationDir destinationDir;

	Sources sources;

	public CompilationConfig(String destinationDir, String sources) {
		super();
		this.destinationDir = new DestinationDir(new File(destinationDir));
		this.sources = new Sources();
		this.sources.add(new File(sources));
	}

	public File getMetaGenericsIntermidateSourceDir() {
		String path = destinationDir.getDir().getPath();
		path += File.separator + META_SRC;
		File file = new File(path);
		if (file.isFile())
			throw new RuntimeException("Destination dir is file.");
		return file;
	}
}
