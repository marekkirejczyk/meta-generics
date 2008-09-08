package metagenerics.unit;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.jar.JarFile;

import metagenerics.TestHelper;
import metagenerics.pipe.common.ClassPath;
import metagenerics.symbol.PackageSymbol;
import metagenerics.symbol.type.ClassSymbol;

import org.junit.Before;
import org.junit.Test;

public class ClassPathTest {

	ClassPath cp;

	String testFolder = TestHelper.getUnitTestFileName("jartools");

	@Before
	public void before() throws IOException {
		cp = new ClassPath();
	}

	@Test
	public void testFindingLib() throws ClassNotFoundException {
		assertTrue(cp.getJarfiles().size() > 0);
		assertTrue(cp.lookup("javax.net.SocketFactory") instanceof ClassSymbol);
		assertTrue(cp.lookup("javax.net") instanceof PackageSymbol);
	}

	@Test
	public void testFindingExtensions() throws ClassNotFoundException {
		assertTrue(cp.getJarfiles().size() > 0);
		assertTrue(cp.lookup("com.sun.crypto.provider.AESCipher") instanceof ClassSymbol);
		assertTrue(cp.lookup("com.sun.crypto.provider") instanceof PackageSymbol);
	}

	@Test
	public void testPathResolving() {
		String path = "/usr/lib/jvm/java-6-sun-1.6.0.06/jre/lib/ext:/usr/java/packages/lib/ext";
		List<String> pathes = cp.getPathsList(path);
		assertEquals(2, pathes.size());
		assertEquals(pathes.get(0),
				"/usr/lib/jvm/java-6-sun-1.6.0.06/jre/lib/ext");
		assertEquals(pathes.get(1), "/usr/java/packages/lib/ext");
	}

	@Test
	public void testGetJarListFromFolder() throws IOException {

		String jar1 = TestHelper.getUnitTestFileName("jartools/junit-4.1.jar");
		String jar2 = TestHelper.getUnitTestFileName("jartools/rt.jar");
		List<JarFile> files = cp.getJarListFromFolder(testFolder);
		assertEquals(2, files.size());
		assertEquals(jar1, files.get(0).getName());
		assertEquals(jar2, files.get(1).getName());
	}

	@Test
	public void testFolderLoading() throws IOException, ClassNotFoundException {
		cp.addFolder(testFolder);
		ClassSymbol klass = (ClassSymbol)cp.lookup("junit.framework.Assert");
		assertTrue(klass instanceof ClassSymbol);
		assertEquals("Assert", klass.getName());
		assertTrue(cp.lookup("junit.framework") instanceof PackageSymbol);
		assertTrue(cp.lookup("junit.frameworka") == null);
	}

}
