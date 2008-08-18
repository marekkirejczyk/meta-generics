package metagenerics.walkers;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

import metagenerics.exception.CompileException;
import util.FolderUtils;

public class MetaCompilerWalker {
	MetaPreCompilerWalker preCompiler = new MetaPreCompilerWalker();

	SourceListWalker sourceListWalker = new SourceListWalker();

	String targetFolder;

	public String getIntermediateFolder() {
		return preCompiler.getIntermediateFolder();
	}

	public List<String> getSourceFolders() {
		return preCompiler.getSourceFolders();
	}

	public void setIntermediateFolder(String intermediateFolder) {
		preCompiler.setIntermediateFolder(intermediateFolder);
	}

	public void setSourceFolders(List<String> sourceFolders) {
		preCompiler.setSourceFolders(sourceFolders);
	}

	public String getTargetFolder() {
		return targetFolder;
	}

	public void setTargetFolder(String targetFolder) {
		this.targetFolder = targetFolder;
	}

	DiagnosticListener diagnosticListener = new DiagnosticListener<JavaFileObject>() {
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

		String t = "test/data/integration/accessors/bin/precompiled/Person.class";
		Iterable<String> x = Collections.singleton(t);

		CompilationTask task = compiler.getTask(null, fileManager,
				diagnosticListener, Arrays.asList(new String[] { "-d",
						targetFolder }), null, compilationUnits);

		task.call();
	}

	private void doCompile() {
		String srcFolder = preCompiler.getPrecompiledCodeIntermediateFolder();
		sourceListWalker.walk(srcFolder);
		List<File> files = sourceListWalker.getList();
		compileFiles(files);
	}

	public void compile() throws IOException {
		preCompiler.compile();
		FolderUtils.ensureFolderExist(getTargetFolder());
		doCompile();
	}

}
