package trash.symbol;

import java.util.List;

import sun.tools.jar.resources.jar;
import util.ArrayUtils;

public class UnitSymbols {
	List<PackageSymbol> importedPackages;

	List<ComplexSymbol> importedSymbols;

	List<ComplexSymbol> declaratedSymbol;

	protected void addImportPackage(String path) {
		path = path.substring(0, path.length()-1);
		Package p = Package.getPackage(path);
	}

	protected void addImportClass(String path) {
		
	}

	public void addImportPath(String path) {
		String p[] = path.split(".");
		if (ArrayUtils.lastElement(p) == "*")
			addImportPackage(path);
		else
			addImportClass(path);
	}
}
