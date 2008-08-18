package metagenerics.io;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.rmi.UnexpectedException;


public class SourcesWalker {
	Sources sources;

	DestinationDir destinationDir;

	FileTransform transform;

	FilenameFilter filter = new FilenameFilter() {
		@Override
		public boolean accept(File dir, String name) {
			return true;
		}
	};

	public void walk() {
		try {
			for (File dir : sources.getDirecotries())
				walk(dir, destinationDir.getDir().getPath() + File.separator);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void walk(File file, String base) throws IOException {
		if (file.isFile()) {
			if (filter.accept(file.getParentFile(), file.getName()))
				transform.compile(file, new File(base));
		} else if (file.isDirectory())
			for (File currentFile : file.listFiles())
				walk(currentFile, base + currentFile.getName() + File.separator);
		else
			throw new UnexpectedException("File is not dir or file");
	}

	public SourcesWalker(Sources sources, DestinationDir destinationDir,
			FileTransform transform) {
		this.sources = sources;
		this.destinationDir = destinationDir;
		this.transform = transform;
	}

	public SourcesWalker(String sources, String destinationDir,
			FileTransform transform) {
		this.sources = new Sources();
		this.sources.add(new File(sources));
		this.destinationDir = new DestinationDir(new File(destinationDir));
		this.transform = transform;
	}

	public Sources getSources() {
		return sources;
	}

	public void setSources(Sources sources) {
		this.sources = sources;
	}

	public DestinationDir getDestinationDir() {
		return destinationDir;
	}

	public void setDestinationDir(DestinationDir destinationDir) {
		this.destinationDir = destinationDir;
	}

	public FileTransform getTransform() {
		return transform;
	}

	public void setTransform(FileTransform transform) {
		this.transform = transform;
	}

	public FilenameFilter getFilter() {
		return filter;
	}

	public void setFilter(final FilenameFilter filter) {
		this.filter = filter;
	}

}
