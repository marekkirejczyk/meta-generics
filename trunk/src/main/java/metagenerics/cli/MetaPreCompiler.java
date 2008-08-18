package metagenerics.cli;

import java.io.IOException;
import java.util.List;

import metagenerics.walkers.MetaPreCompilerWalker;

public class MetaPreCompiler extends CommandLineCompiler {

	MetaPreCompilerWalker compiler = new MetaPreCompilerWalker();

	@Override
	void printInfo() {
		String msg = "\tmetaprecompiler -src <dir:dir:...> -intermediate <dir> -target <dir>";
		System.out.println("Usage:");
		System.out.println(msg);
	}

	@Override
	void setIntermediateFolder(String folder) {
		compiler.setMetaGenericsIntermediateFolder(folder);
	}

	@Override
	void setSourceFolders(List<String> folders) {
		compiler.setSourceFolders(folders);
	}

	@Override
	void setTargetFolder(String folder) {
		compiler.setPrecompiledCodeIntermediateFolder(folder);
	}

	@Override
	void compile() throws IOException {
		compiler.compile();
	}

	@Override
	String getIntermediateFolder() {
		return compiler.getMetaGenericsIntermediateFolder();
	}

	@Override
	List<String> getSourceFolders() {
		return compiler.getSourceFolders();
	}

	@Override
	String getTargetFolder() {
		return compiler.getPrecompiledCodeIntermediateFolder();
	}
	
	static public void main (String [] args) {
		new MetaPreCompiler().run(args);
	}

}
