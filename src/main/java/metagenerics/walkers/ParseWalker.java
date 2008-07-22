package metagenerics.walkers;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import metagenerics.ast.unit.Unit;
import metagenerics.exception.CompileException;
import metagenerics.transform.parse.MetaJavaParser;

import org.antlr.runtime.RecognitionException;

import util.PathUtils;

public class ParseWalker {

	Map<String, Unit> units = new TreeMap<String, Unit>();

	MetaJavaParser parser = new MetaJavaParser();

	String baseFolder;

	public void parseFolder(String filename) {
		baseFolder = filename;
		visitFolder(filename);
	}

	public Collection<Unit> getUnits() {
		return units.values();
	}

	public Map<String, Unit> getUnitsWithNames() {
		return units;
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

	private void visitFile(String file) {
		try {
			String path = PathUtils.pathDiff(file, baseFolder);
			units.put(path, parser.parse(file));
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

}
