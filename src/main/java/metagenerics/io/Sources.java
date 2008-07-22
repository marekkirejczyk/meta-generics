package metagenerics.io;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Sources {
	List<File> dirs = new ArrayList<File>();

	public boolean add(File dir) {
		if (!dir.isDirectory())
			throw new RuntimeException(dir.getPath() + " is not a dir");
		return dirs.add(dir);
	}

	public boolean remove(File o) {
		return dirs.remove(o);
	}

	public Collection<File> getDirecotries() {
		return Collections.unmodifiableCollection(dirs);
	}
}
