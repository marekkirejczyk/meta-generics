package metagenerics.unit;

import static metagenerics.TestHelper.getUnitTestFileName;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import metagenerics.pipe.common.JarClassLoader;

import org.junit.Ignore;
import org.junit.Test;

public class JarToolsTest {
	@Ignore
	@Test
	public void test() {
		// java.ext.dirs
		String dirs = System.getProperty("java.class.path");
		for (String s : dirs.split(":"))
			System.out.println(s);
		System.out.println(System.getProperty("java.home"));
	}

	void handleClassFile(JarFile file, JarEntry entry)
			throws ClassNotFoundException {
		if (entry.getName().matches(".*\\$\\d*\\..*")) {
			return;
		}

		JarClassLoader loader = new JarClassLoader(file);

		String name = entry.getName().replace(File.separator, ".");

		name = name.substring(0, name.length() - 6);
		// System.out.println(" start: " + name);
		Class<? extends Object> klass = loader.loadClass(name);
		System.out.println(klass.getName());
		for (Method m : klass.getMethods()) {
			System.out.print(" " + m.getName() + "(");
			for (Class<? extends Object> p : m.getParameterTypes())
				System.out.print(p.getName() + " ");
			System.out.println(")");
		}
	}

	@Ignore
	@Test
	public void jarTest() throws IOException, ClassNotFoundException {
		String jarFileName = getUnitTestFileName("jartools/rt.jar");
		JarFile jarFile = new JarFile(jarFileName);
		for (Enumeration<JarEntry> e = jarFile.entries(); e.hasMoreElements();) {
			JarEntry entry = e.nextElement();
			if (!entry.isDirectory() && entry.getName().endsWith(".class"))
				handleClassFile(jarFile, entry);

		}
	}
}
