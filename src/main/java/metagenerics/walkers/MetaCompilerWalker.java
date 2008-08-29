package metagenerics.walkers;

import java.io.IOException;
import java.util.List;

import util.FolderUtils;

public class MetaCompilerWalker {
	private MetaPreCompilerWalker preCompiler = new MetaPreCompilerWalker();

	private String targetFolder;

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

	public void compile() throws IOException {
		preCompiler.compile();
		FolderUtils.ensureFolderExist(getTargetFolder());
		new JavaCompilerWalker(preCompiler
				.getPrecompiledCodeIntermediateFolder(), getTargetFolder());
	}

}
