package metagenerics.pipe.common;

import static util.FolderUtils.ensureFolderExist;

import java.io.FileWriter;
import java.io.IOException;

import metagenerics.ast.UnitAsts;
import util.PathUtils;

public class AstForestSaver {

	String folder;

	UnitAsts units;

	private AstForestSaver(String folder, UnitAsts units) {
		this.folder = folder;
		this.units = units;
	}

	public void saveAstForest() throws IOException {
		PrettyPrinter printer = new PrettyPrinter();
		for (String filename : units.keySet()) {
			String fullName = folder + filename;
			ensureFolderExist(PathUtils.stripFileName(fullName));
			FileWriter writer = new FileWriter(fullName);
			printer.setAppendable(writer);
			printer.visit(units.get(filename));
			writer.close();
		}
	}

	static public void save(String folder, UnitAsts units)
			throws IOException {
		new AstForestSaver(folder, units).saveAstForest();
	}

}
