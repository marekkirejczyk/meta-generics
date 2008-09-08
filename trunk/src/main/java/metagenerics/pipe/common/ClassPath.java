package metagenerics.pipe.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import metagenerics.exception.UnresolvedClassPath;
import metagenerics.symbol.PackageSymbol;
import metagenerics.symbol.Symbol;
import metagenerics.symbol.type.ClassSymbol;

import util.Assert;
import util.FileNameExtensionFilter;

public class ClassPath {
	List<JarFile> jarfiles = new ArrayList<JarFile>();

	public ClassPath() {
		String extension = System.getProperty("java.ext.dirs");
		addClassPath(extension);
		String lib = System.getProperty("sun.boot.class.path");
		addClassPath(lib);
	}

	public void addJarFile(String filename) {
		try {
			jarfiles.add(new JarFile(filename));
		} catch (IOException e) {
			throw new UnresolvedClassPath();
		}
	}

	public void addFolder(String folderName) {
		jarfiles.addAll(getJarListFromFolder(folderName));
	}

	public void addClassPath(String classPathString) {
		for (String item : getPathsList(classPathString)) {
			File file = new File(item);
			if (file.isDirectory())
				addFolder(item);
			else if (file.isFile())
				addJarFile(item);
		}
	}

	public List<JarFile> getJarListFromFolder(String folderPath) {
		File folder = new File(folderPath);
		Assert.assertTrue(folder.isDirectory());
		List<JarFile> result = new ArrayList<JarFile>();
		for (File file : folder.listFiles(new FileNameExtensionFilter("jar")))
			try {
				result.add(new JarFile(file));
			} catch (IOException e) {
				throw new UnresolvedClassPath();
			}
		return result;
	}

	public List<String> getPathsList(String classPathString) {
		return Arrays.asList(classPathString.split(File.pathSeparator));
	}

	ClassSymbol classLookup(String packagePath) throws ClassNotFoundException {
		String filePath = packagePath.replace(".", File.separator) + ".class";
		for (JarFile jarFile : jarfiles) {
			JarEntry entry = jarFile.getJarEntry(filePath);
			if (entry != null && !entry.isDirectory())
				return new ClassSymbol(new JarClassLoader(jarFile).loadClass(packagePath));
		}
		return null;
	}

	PackageSymbol packageLookup(String packagePath) {
		String folderPath = packagePath.replace(".", File.separator)
				+ File.separator;
		PackageSymbol aPackage = null;
		for (JarFile jarFile : jarfiles) {
			JarEntry entry = jarFile.getJarEntry(folderPath);
			if (entry != null && entry.isDirectory()) {
				if (aPackage == null)
					aPackage = new PackageSymbol("");

			}
		}
		return aPackage;
	}

	public Symbol lookup(String packagePath) throws ClassNotFoundException {
		ClassSymbol klass = classLookup(packagePath);
		if (klass != null)
			return klass;
		return packageLookup(packagePath);
	}

	public List<JarFile> getJarfiles() {
		return jarfiles;
	}

}
