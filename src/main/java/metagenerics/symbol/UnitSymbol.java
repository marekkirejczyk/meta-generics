package metagenerics.symbol;

import java.util.ArrayList;
import java.util.List;

import metagenerics.ast.Node;
import metagenerics.ast.unit.UnitAst;
import metagenerics.exception.AmbiguousSymbolException;
import metagenerics.symbol.type.Type;



public class UnitSymbol extends Symbol {

	List<PackageSymbol> packageImports = new ArrayList<PackageSymbol>();

	List<Type> typeImports = new ArrayList<Type>();

	public UnitSymbol(UnitAst ast) {
		setAstNode(ast);
		setName("");
	}

	public Symbol importsLookup(String name) {
		List<? extends Symbol> result;

		result = typeImportsLookup(name);

		if (result.size() == 1)
			return result.get(0);
		else if (result.size() > 1)
			throw new AmbiguousSymbolException(name);

		result = generalImportsLookup(name);

		if (result.size() == 1)
			return result.get(0);
		else if (result.size() > 1)
			throw new AmbiguousSymbolException(name);

		return null;
	}

	private List<Type> generalImportsLookup(String name) {
		List<Type> result = new ArrayList<Type>();
		for (PackageSymbol aPackage: packageImports)
			if (aPackage.getSymbol(name) != null)
				result.add((Type)(aPackage.getSymbol(name)));
		return result;
	}

	private List<Type> typeImportsLookup(String name) {
		List<Type> result = new ArrayList<Type>();
		for (Type type : typeImports)
			if (type.getName().equals(name))
				result.add(type);
		return result;
	}

	public List<PackageSymbol> getPackageImports() {
		return packageImports;
	}

	public void setPackageImports(List<PackageSymbol> packageImports) {
		this.packageImports = packageImports;
	}

	public List<Type> getTypeImports() {
		return typeImports;
	}

	public void setTypeImports(List<Type> symbolImports) {
		this.typeImports = symbolImports;
	}

	public void addPackageImport(PackageSymbol arg0) {
		packageImports.add(arg0);
	}

	public void addTypeImport(Type arg0) {
		typeImports.add(arg0);
	}

	@Override
	public UnitAst getAstNode() {
		return (UnitAst)super.getAstNode();
	}
	
	

}
