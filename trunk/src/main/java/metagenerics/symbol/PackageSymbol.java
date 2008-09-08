package metagenerics.symbol;

import java.util.ArrayList;
import java.util.List;

import metagenerics.pipe.common.ClassPath;

import util.CollectionUtils;

public class PackageSymbol extends Symbol {
	private List<UnitSymbol> units = new ArrayList<UnitSymbol>();

	ClassPath classPath;
	
	public ClassPath getClassPath() {
		return classPath;
	}

	public void setClassPath(ClassPath classPath) {
		this.classPath = classPath;
	}

	private PackageSymbol getPackage(String name) {
		PackageSymbol packageSymbol = (PackageSymbol) getSymbol(name);
		if (packageSymbol != null)
			return packageSymbol;
		packageSymbol = new PackageSymbol(name);
		packageSymbol.setParent(this);
		add(packageSymbol);
		return packageSymbol;
	}

	public PackageSymbol getSubPackage(List<String> subPath) {
		if (subPath.size() == 0)
			return this;
		PackageSymbol next = getPackage(subPath.get(0));
		CollectionUtils.removeFirst(subPath);
		return next.getSubPackage(subPath);
	}

	public PackageSymbol(String name) {
		setName(name);
	}

	public Symbol importsLookup(String name) {
		return null;
	}

	public void addUnit(UnitSymbol unit) {
		units.add(unit);
	}

}
