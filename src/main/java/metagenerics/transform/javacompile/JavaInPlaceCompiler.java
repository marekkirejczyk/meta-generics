package metagenerics.transform.javacompile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import metagenerics.exception.CompileException;
import util.PathUtils;

public class JavaInPlaceCompiler {

	public Class compile(String sourceDirectory, String klass)
			throws IOException, ClassNotFoundException {
		String fileName = PathUtils.sourceDirectoryAndClassToJavaFileName(
				sourceDirectory, klass);
		File[] files1 = { new File(fileName) };
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(
				null, null, null);

		Iterable<? extends JavaFileObject> compilationUnits1 = fileManager
				.getJavaFileObjectsFromFiles(Arrays.asList(files1));

		compiler.getTask(null, fileManager, new DiagnosticListener<JavaFileObject>() {
			public void report(Diagnostic arg0) {
				System.err.println(arg0);
				throw new CompileException();
			}
		}, null, null, compilationUnits1).call();

		CustomDirClassLoader loader = new CustomDirClassLoader(sourceDirectory);
		return loader.loadClass(klass);
	}
}
