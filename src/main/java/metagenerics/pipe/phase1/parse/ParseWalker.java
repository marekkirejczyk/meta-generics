package metagenerics.pipe.phase1.parse;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import metagenerics.ast.UnitAsts;
import metagenerics.ast.unit.UnitAst;
import metagenerics.exception.CompileException;

import org.antlr.runtime.RecognitionException;

import util.PathUtils;

public class ParseWalker {

	Map<String, UnitAst> units = new TreeMap<String, UnitAst>();

	MetaJavaParser parser = new MetaJavaParser();

	String baseFolder;

	public void parseFolder(String filename) {
		baseFolder = filename;
		visitFolder(filename);
	}

	public Collection<UnitAst> getUnits() {
		return units.values();
	}

	public Map<String, UnitAst> getUnitsWithNames() {
		return units;
	}
	
	public UnitAsts getUnitAsts() {
		return new UnitAsts(units);
	}

	private void visit(String filename) {
		File file = new File(filename);
		if (file.isDirectory())
			visitFolder(filename);
		else if (file.exists())
			visitFile(filename);
		else
			throw new CompileException("Not a directory or file: " + filename);
	}

	private void visitFile(String fileName) {
		if (!SourceFiles.isSourceFileName(fileName))
			return;
		try {
			String path = PathUtils.pathDiff(fileName, baseFolder);
			units.put(path, parser.parse(fileName));
		} catch (IOException e) {
			throw new CompileException(e);
		} catch (RecognitionException e) {
			throw new CompileException(e);
		}
	}

	private void visitFolder(String folderPath) {
		File folder = new File(folderPath);
		for (File file : folder.listFiles())
			visit(file.getPath());
	}
	
	
	static public UnitAsts parseFolders(Collection<String> sourceFolders) {
		ParseWalker walker = new ParseWalker();
		for (String sourceFolder : sourceFolders)
			walker.parseFolder(sourceFolder);
		return walker.getUnitAsts();
	}

}
