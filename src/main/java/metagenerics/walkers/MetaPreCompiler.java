package metagenerics.walkers;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import metagenerics.ast.unit.Unit;
import metagenerics.exception.CompileException;
import metagenerics.transform.parse.PrettyPrinter;
import metagenerics.visitors.MetaGenericBuilder;
import metagenerics.visitors.SymbolTableBuilder;
import metagenerics.visitors.TypedefBuilder;
import util.FolderUtils;
import util.PathUtils;

public class MetaPreCompiler {

	List<String> sourceFolders;

	String intermediateFolder;

	Map<String, Unit> units;

	public void parse() {
		ParseWalker walker = new ParseWalker();
		for (String sourceFolder : sourceFolders)
			walker.parseFolder(sourceFolder);
		units = walker.getUnitsWithNames();
	}

	public void buildSymbolTables() {
		SymbolTableBuilder builder = new SymbolTableBuilder();
		for (Unit unit : units.values())
			builder.visit(unit);
	}

	public void transformMetaGenerics() {
		MetaGenericBuilder builder = new MetaGenericBuilder();
		builder.setIntermediateFolder(getMetaGenericsIntermediateFolder());
		for (Unit unit : units.values())
			builder.visit(unit);
	}

	public void transformMetaTypedefs() {
		TypedefBuilder builder = new TypedefBuilder();
		for (Unit unit : units.values())
			builder.visit(unit);
	}

	public void saveIntermediateFiles() throws IOException {
		PrettyPrinter printer = new PrettyPrinter();

		for (String filename : units.keySet()) {
			String fullName = getPrecompiledCodeIntermediateFolder() + filename;
			FolderUtils.ensureFolderExist(PathUtils.stripFileName(fullName));
			FileWriter writer = new FileWriter(fullName);
			printer.setAppendable(writer);
			printer.visit(units.get(filename));
			writer.close();
		}
	}

	public void ensureFoldersExists() {
		try {
			FolderUtils.ensureFolderExist(getIntermediateFolder());
			FolderUtils.ensureFolderExist(getMetaGenericsIntermediateFolder());
			FolderUtils
					.ensureFolderExist(getPrecompiledCodeIntermediateFolder());
		} catch (IOException e) {
			throw new CompileException(e);
		}
	}

	public void compile() throws IOException {
		ensureFoldersExists();
		
		parse();
		buildSymbolTables();
		transformMetaGenerics();
		transformMetaTypedefs();
		saveIntermediateFiles();
	}

	public String getIntermediateFolder() {
		return intermediateFolder;
	}

	public String getMetaGenericsIntermediateFolder() {
		return intermediateFolder + "metagenerics";
	}

	public String getPrecompiledCodeIntermediateFolder() {
		return intermediateFolder + "precompiled/";
	}

	public void setIntermediateFolder(String intermediateFolder) {
		this.intermediateFolder = intermediateFolder;
	}

	public List<String> getSourceFolders() {
		return sourceFolders;
	}

	public void setSourceFolders(List<String> sourceFolders) {
		this.sourceFolders = sourceFolders;
	}

	public Collection<Unit> getUnits() {
		return units.values();
	}

}
