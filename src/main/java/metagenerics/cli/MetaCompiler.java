package metagenerics.cli;

import java.io.IOException;
import java.util.List;

import metagenerics.walkers.MetaCompilerWalker;

public class MetaCompiler extends CommandLineCompiler {

	private MetaCompilerWalker compiler = new MetaCompilerWalker();

	public MetaCompilerWalker getCompiler() {
		return compiler;
	}

	void printInfo() {
		String msg = "\tmetacompiler -src <dir:dir:...> -intermediate <dir> -target <dir>";
		System.out.println("Usage:");
		System.out.println(msg);
	}

	static public void main(String[] args) {
		new MetaCompiler().run(args);
	}

	@Override
	void setIntermediateFolder(String folder) {
		compiler.setIntermediateFolder(folder);
	}

	@Override
	void setSourceFolders(List<String> folders) {
		compiler.setSourceFolders(folders);
	}

	@Override
	void setTargetFolder(String folder) {
		compiler.setTargetFolder(folder);
	}

	@Override
	void compile() throws IOException {
		compiler.compile();

	}

	@Override
	String getIntermediateFolder() {
		return compiler.getIntermediateFolder();
	}

	@Override
	List<String> getSourceFolders() {
		return compiler.getSourceFolders();
	}

	@Override
	String getTargetFolder() {
		return compiler.getTargetFolder();
	}
}
