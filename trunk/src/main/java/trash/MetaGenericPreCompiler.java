package trash;


import java.io.File;

import util.Assert;
import util.PathUtils;

public class MetaGenericPreCompiler {
	
	File sourceDirectory;

	File targetDirectory;

	UnitCompiler unitCompiler = new UnitCompiler();

	public MetaGenericPreCompiler(File sourceDirectory, File targetDirectory) {
		Assert.assertTrue(sourceDirectory.isDirectory());
		Assert.assertTrue(targetDirectory.isDirectory());
		this.sourceDirectory = sourceDirectory;
		this.targetDirectory = targetDirectory;
	}

	protected void compileFile(File file) {
		String pathEnding = PathUtils.pathDiff(file.getAbsolutePath(),
				sourceDirectory.getAbsolutePath());
		String outFile = targetDirectory.getAbsolutePath() + pathEnding;
		String outDir = PathUtils.stripFileName(outFile);
		unitCompiler.compile(file, outDir);
	}

	protected void compileDirectory(File directory) {
		Assert.assertTrue(directory.isDirectory());
		for (File file : directory.listFiles())
			if (file.isDirectory())
				compileDirectory(file);
			else if (file.isFile() && file.getName().endsWith(".java"))
				compileFile(file);
	}

	public void compile() {
		unitCompiler.setOutputDirectory(targetDirectory.getAbsolutePath());
		compileDirectory(sourceDirectory);
	}
}
