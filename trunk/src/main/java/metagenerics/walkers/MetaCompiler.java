package metagenerics.walkers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import util.FolderUtils;

import metagenerics.exception.CompileException;

public class MetaCompiler {
	MetaPreCompiler preCompiler = new MetaPreCompiler();

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

	private void doCompile() {
		String srcFolder = preCompiler.getPrecompiledCodeIntermediateFolder();
		sourceListWalker.walk(srcFolder);
		System.out.println(srcFolder);
		List<File> files = sourceListWalker.getList();
		System.out.println(files);
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(
				null, null, null);

		Iterable<? extends JavaFileObject> compilationUnits1 = fileManager
				.getJavaFileObjectsFromFiles(files);
		compiler.getTask(null, fileManager, new DiagnosticListener<JavaFileObject>() {
			public void report(Diagnostic arg0) {
				System.err.println(arg0);
				throw new CompileException();
			}
		}, null, null, compilationUnits1).call();
		
	}

	public void compile() throws IOException {
		preCompiler.compile();
		FolderUtils.ensureFolderExist(getTargetFolder());
		doCompile();
	}

}
