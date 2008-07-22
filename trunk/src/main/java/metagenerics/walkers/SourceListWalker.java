package metagenerics.walkers;

import java.io.File;
import java.io.IOError;
import java.util.ArrayList;
import java.util.List;

public class SourceListWalker {

	List<File> list = new ArrayList<File>();
	
	public void walk(String path) {
		File file = new File(path);
		if (file.isFile() && path.endsWith(".java"))
			list.add(file);
		else if (file.isDirectory()) 
			for (String filename: file.list())
				walk(path + File.separator + filename);
		else if (!file.isFile())
			throw new RuntimeException("Bad file/dir: " + file.getPath());
	}

	public List<File> getList() {
		return list;
	}

}
