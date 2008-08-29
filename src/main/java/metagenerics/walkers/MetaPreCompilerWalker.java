package metagenerics.walkers;

import static util.FolderUtils.ensureFolderExist;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import metagenerics.ast.unit.UnitAst;
import metagenerics.exception.CompileException;
import metagenerics.visitors.MetaGenericBuilder;
import metagenerics.visitors.MetaGenericCompilerBuilder;
import metagenerics.visitors.SymbolTableBuilder;
import metagenerics.visitors.TypedefBuilder;

public class MetaPreCompilerWalker {

	List<String> sourceFolders;

	String intermediateFolder;

	String metaGenericsIntermediateFolder;

	String precompiledCodeIntermediateFolder;

	Map<String, UnitAst> units;

	public void buildSymbolTables() {
		SymbolTableBuilder builder = new SymbolTableBuilder();
		builder.build(units.values());
	}

	public void transformMetaGenerics() throws IOException {
		String folder = getMetaGenericsIntermediateFolder();
		Map<String, UnitAst> copy = new MetaGenericBuilder().build(units);
		AstForestSaver.save(folder, copy);

		new JavaCompilerWalker(folder, folder).compile();
		new MetaGenericCompilerBuilder(folder).build(units);
	}

	public void setMetaGenericsIntermediateFolder(
			String metaGenericsIntermediateFolder) {
		this.metaGenericsIntermediateFolder = metaGenericsIntermediateFolder;
	}

	public void setPrecompiledCodeIntermediateFolder(
			String precompiledCodeIntermediateFolder) {
		this.precompiledCodeIntermediateFolder = precompiledCodeIntermediateFolder;
	}

	void transformMetaTypedefs() {
		TypedefBuilder builder = new TypedefBuilder();
		for (UnitAst unit : units.values())
			builder.visit(unit);
	}

	void transformImports() {
		new ImportsWalker(units).transformImports();
	}

	public void ensureFoldersExists() {
		try {
			if (getIntermediateFolder() != null)
				ensureFolderExist(getIntermediateFolder());
			ensureFolderExist(getMetaGenericsIntermediateFolder());
			ensureFolderExist(getPrecompiledCodeIntermediateFolder());
		} catch (IOException e) {
			throw new CompileException(e);
		}
	}

	public void compile() throws IOException {
		ensureFoldersExists();
		units = ParseWalker.parseFolders(sourceFolders);
		PackageDeclarationsChecker.check(units);
		buildSymbolTables();
		transformImports();
		transformMetaGenerics();
		transformMetaTypedefs();
		AstForestSaver.save(getPrecompiledCodeIntermediateFolder(), units);
	}

	public String getIntermediateFolder() {
		return intermediateFolder;
	}

	public String getMetaGenericsIntermediateFolder() {
		return metaGenericsIntermediateFolder;
	}

	public String getPrecompiledCodeIntermediateFolder() {
		return precompiledCodeIntermediateFolder;
	}

	public void setIntermediateFolder(String intermediateFolder) {
		this.intermediateFolder = intermediateFolder;
		this.precompiledCodeIntermediateFolder = intermediateFolder
				+ "/precompiled";
		this.metaGenericsIntermediateFolder = intermediateFolder
				+ "/metagenerics";
	}

	public List<String> getSourceFolders() {
		return sourceFolders;
	}

	public void setSourceFolders(List<String> sourceFolders) {
		this.sourceFolders = sourceFolders;
	}

	public Collection<UnitAst> getUnits() {
		return units.values();
	}

}
