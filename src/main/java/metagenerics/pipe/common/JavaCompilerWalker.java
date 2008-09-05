package metagenerics.pipe.common;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

import metagenerics.exception.CompileException;
import metagenerics.io.SourceListWalker;
import util.FolderUtils;

public class JavaCompilerWalker {
	String sourceFolder;

	String targetFolder;

	public JavaCompilerWalker(String sourceFolder, String targetFolder) {
		super();
		this.sourceFolder = sourceFolder;
		this.targetFolder = targetFolder;
	}

	public String getSourceFolder() {
		return sourceFolder;
	}

	public void setSourceFolder(String sourceFolder) {
		this.sourceFolder = sourceFolder;
	}

	public String getTargetFolder() {
		return targetFolder;
	}

	public void setTargetFolder(String targetFolder) {
		this.targetFolder = targetFolder;
	}

	DiagnosticListener<JavaFileObject> diagnosticListener = new DiagnosticListener<JavaFileObject>() {
		public void report(Diagnostic arg0) {
			System.err.println(arg0);
			throw new CompileException();
		}
	};

	private void compileFiles(List<File> files) {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(
				null, null, null);

		Iterable<? extends JavaFileObject> compilationUnits = fileManager
				.getJavaFileObjectsFromFiles(files);

		CompilationTask task = compiler.getTask(null, fileManager,
				diagnosticListener, Arrays.asList(new String[] { "-d",
						targetFolder }), null, compilationUnits);

		task.call();
	}

	public void compile() throws IOException {
		FolderUtils.ensureFolderExist(getTargetFolder());
		SourceListWalker sourceListWalker = new SourceListWalker();
		sourceListWalker.walk(sourceFolder);
		List<File> files = sourceListWalker.getList();
		compileFiles(files);
	}

}
