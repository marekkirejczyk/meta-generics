package metagenerics.pipe.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import util.Assert;

public class JarClassLoader extends ClassLoader {

	JarFile jarFile;

	public JarClassLoader(JarFile jarFile) {
		this.jarFile = jarFile;
	}

	public Class<?> findClass(String name) throws ClassNotFoundException {
		try {
			byte[] b = loadClassData(name);
			return defineClass(name, b, 0, b.length);
		} catch (IOException e) {
			throw new ClassNotFoundException("Class file not found", e);
		}
	}

	public String classNameToPath(String arg) {
		String path = arg.replace('.', File.separatorChar);
		return jarFile + File.separator + path + ".class";
	}

	private byte[] loadClassData(String binaryClassName) throws IOException {
		String fileName = binaryClassName.replace(".", File.separator);
		fileName = fileName.replace(File.separator + "class", ".class");
		JarEntry entry = jarFile.getJarEntry(fileName);
		System.out.println(fileName);
		InputStream is = jarFile.getInputStream(entry);
		int size = (int)entry.getSize(); 
		byte[] result = new byte[size];
		Assert.assertTrue(is.read(result, 0, size) == size);
		is.close();
		return result;
	}
}
