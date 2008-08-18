package metagenerics.unit;

import static metagenerics.TestHelper.getUnitTestFileName;

import java.io.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import metagenerics.runtime.MetaGeneric;
import metagenerics.transform.javacompile.CustomDirClassLoader;
import metagenerics.transform.javacompile.JavaInPlaceCompiler;

import org.junit.Assert;
import org.junit.Test;

import util.PathUtils;

public class JavaCompilerTest {

	final static String CLASS_CANONICAL_NAME = "somePackage.Calculator";

	final static String SOURCE_FOLDER = getUnitTestFileName("JavaCompiler");

	@Test
	public void genericTest() throws InstantiationException,
			IllegalAccessException, IOException, ClassNotFoundException {
		String classFileName = PathUtils
				.sourceDirectoryAndClassToClassFileName(SOURCE_FOLDER, CLASS_CANONICAL_NAME);
		File classFile = new File(classFileName);
		if (classFile.exists())
			classFile.delete();

		JavaInPlaceCompiler compiler = new JavaInPlaceCompiler();
		Class<MetaGeneric> genericClass = compiler
				.compile(SOURCE_FOLDER, CLASS_CANONICAL_NAME);
		Assert.assertNotNull(genericClass);
		Assert.assertEquals(Object.class, genericClass.getSuperclass());


	}

	@Test
	public void compilerApiTest() throws IOException, ClassNotFoundException,
			SecurityException, NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException,
			InstantiationException {
		File[] files1 = { new File(
				"test/data/unit/JavaCompiler/somePackage/Calculator.java") }; // input

		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(
				null, null, null);

		Iterable<? extends JavaFileObject> compilationUnits1 = fileManager
				.getJavaFileObjectsFromFiles(Arrays.asList(files1));
		compiler
				.getTask(null, fileManager, null, null, null, compilationUnits1)
				.call();

		fileManager.close();

		CustomDirClassLoader loader = new CustomDirClassLoader(
				"test/data/unit/JavaCompiler/");
		Class c = loader.loadClass("somePackage.Calculator");
		Object o = c.newInstance();
		Method m = c.getMethod("get", null);
		Object result = m.invoke(o, null);
		Assert.assertEquals(result, 13);
		m = c.getMethod("add", int.class, int.class);
		result = m.invoke(o, 2, 3);
		Assert.assertEquals(result, 5);
	}

}
