package metagenerics.pipe;

import static metagenerics.pipe.phase2.MetaGenericTransformations.addCompliedMetagenericsToAst;
import static metagenerics.pipe.phase2.MetaGenericTransformations.addMetagenericsFactories;
import static metagenerics.pipe.phase2.MetaGenericTransformations.filterNonMetaElements;
import static metagenerics.pipe.phase2.MetaGenericTransformations.filterNonMetaImports;
import static metagenerics.pipe.phase2.MetaGenericTransformations.transformMetaGenericsIntoClasses;
import static util.FolderUtils.ensureFolderExist;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import metagenerics.ast.UnitAsts;
import metagenerics.ast.unit.UnitAst;
import metagenerics.exception.CompileException;
import metagenerics.pipe.common.AstForestSaver;
import metagenerics.pipe.common.JavaCompilerWalker;
import metagenerics.pipe.phase1.ImportsWalker;
import metagenerics.pipe.phase1.PackageDeclarationsChecker;
import metagenerics.pipe.phase1.parse.ParseWalker;
import metagenerics.pipe.phase1.symbol.SymbolTableBuilder;
import metagenerics.pipe.phase3.TypedefBuilder;

public class MetaJavaPreCompiler {

	List<String> sourceFolders;

	String intermediateFolder;

	String metaGenericsIntermediateFolder;

	String precompiledCodeIntermediateFolder;

	UnitAsts units;

	public void buildSymbolTables() {
		new SymbolTableBuilder().build(units.values());
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

	private void preparationStage() {
		ensureFoldersExists();
		units = ParseWalker.parseFolders(sourceFolders);
		PackageDeclarationsChecker.check(units);
		buildSymbolTables();
		transformImports();
	}

	private void metaGenericsStage() throws IOException {
		String folder = getMetaGenericsIntermediateFolder();
		transformMetaGenericsIntoClasses(units);
		UnitAsts metaUnits = units.clone();
		filterNonMetaElements(metaUnits);
		filterNonMetaImports(metaUnits);
		addMetagenericsFactories(metaUnits);
		AstForestSaver.save(folder, metaUnits);
		new JavaCompilerWalker(folder, folder).compile();
		addCompliedMetagenericsToAst(folder, metaUnits);
	}

	private void typedefStage() throws IOException {
		transformMetaTypedefs();
		AstForestSaver.save(getPrecompiledCodeIntermediateFolder(), units);
	}

	public void compile() throws IOException {
		preparationStage();
		metaGenericsStage();
		typedefStage();
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
