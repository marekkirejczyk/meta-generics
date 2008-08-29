package metagenerics.walkers;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import metagenerics.ast.unit.PackageDeclaration;
import metagenerics.ast.unit.UnitAst;
import metagenerics.exception.WrongPackageDeclaration;
import util.CollectionUtils;

public class PackageDeclarationsChecker {

	private Map<String, UnitAst> units;
	
	private PackageDeclarationsChecker(Map<String, UnitAst> units) {
		this.units = units;
	}

	void ensurePackageName(List<String> packagePath, String fileName) {
		String[] filePathArray = fileName.split(File.separator);
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
	
	public static void check(Map<String, UnitAst> units) {
		new PackageDeclarationsChecker(units).checkPackages();
	}
}
