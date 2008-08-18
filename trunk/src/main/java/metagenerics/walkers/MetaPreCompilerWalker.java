package metagenerics.walkers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import metagenerics.ast.unit.PackageDeclaration;
import metagenerics.ast.unit.UnitAst;
import metagenerics.exception.CompileException;
import metagenerics.exception.WrongPackageDeclaration;
import metagenerics.transform.parse.PrettyPrinter;
import metagenerics.visitors.MetaGenericBuilder;
import metagenerics.visitors.SymbolTableBuilder;
import metagenerics.visitors.TypedefBuilder;
import util.CollectionUtils;
import util.FolderUtils;
import util.PathUtils;

public class MetaPreCompilerWalker {

	List<String> sourceFolders;

	String intermediateFolder;

	String metaGenericsIntermediateFolder;

	String precompiledCodeIntermediateFolder;

	Map<String, UnitAst> units;

	void ensurePackageName(List<String> packagePath, String fileName) {
		String [] filePathArray = fileName.split(File.separator);
		List<String> filePath = new ArrayList<String>(); 
		filePath.addAll(Arrays.asList(filePathArray));
		CollectionUtils.removeLast(filePath);
		if (filePath.get(0).equals(""))
			CollectionUtils.removeFirst(filePath);
		if (!filePath.equals(packagePath)) 		
		  throw new WrongPackageDeclaration(packagePath, filePath);
	}

	void checkPackages() {
		for (Map.Entry<String, UnitAst> entry : units.entrySet()) {
			String filename = entry.getKey();
			PackageDeclaration packageAst = entry.getValue()
					.getPackageDeclaration();
			List<String> path = packageAst.getPackagePath();
			ensurePackageName(path, filename);
		}
	}

	public void parse() {
		ParseWalker walker = new ParseWalker();
		for (String sourceFolder : sourceFolders)
			walker.parseFolder(sourceFolder);
		units = walker.getUnitsWithNames();
		checkPackages();
	}

	public void buildSymbolTables() {
		SymbolTableBuilder builder = new SymbolTableBuilder();
		builder.build(units.values());
	}

	public void transformMetaGenerics() {
		MetaGenericBuilder builder = new MetaGenericBuilder();
		builder.setIntermediateFolder(getMetaGenericsIntermediateFolder());
		for (UnitAst unit : units.values())
			builder.visit(unit);
	}

	public void setMetaGenericsIntermediateFolder(
			String metaGenericsIntermediateFolder) {
		this.metaGenericsIntermediateFolder = metaGenericsIntermediateFolder;
	}

	public void setPrecompiledCodeIntermediateFolder(
			String precompiledCodeIntermediateFolder) {
		this.precompiledCodeIntermediateFolder = precompiledCodeIntermediateFolder;
	}

	public void transformMetaTypedefs() {
		TypedefBuilder builder = new TypedefBuilder();
		for (UnitAst unit : units.values())
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
			if (getIntermediateFolder() != null)
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
