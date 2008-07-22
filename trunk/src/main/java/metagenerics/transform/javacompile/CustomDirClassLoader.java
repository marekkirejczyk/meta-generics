package metagenerics.transform.javacompile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import util.Assert;

public class CustomDirClassLoader extends ClassLoader {

	String directory;

	public CustomDirClassLoader(String directory) {
		super();
		this.directory = directory;
	}

	public Class<?> findClass(String name) throws ClassNotFoundException {
		try {
			byte[] b = loadClassData(name);
			return defineClass(name, b, 0, b.length);
		} catch (IOException e) {
			throw new ClassNotFoundException ("Class file not found", e);
		}
	}

	public String classNameToPath(String arg) {
		String path = arg.replace('.', File.separatorChar);
		return directory + File.separator + path + ".class";
	}

	private byte[] loadClassData(String name) throws IOException {
		String path = classNameToPath(name);
		int size = (int)(new File(path).length());
		byte[] result = new byte[size];
		FileInputStream is = new FileInputStream(path);
		Assert.assertTrue(is.read(result, 0, size) == size);
		is.close();
		return result;
	}
}
