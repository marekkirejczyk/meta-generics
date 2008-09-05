package metagenerics.symbol;

import java.io.PrintStream;

import metagenerics.ast.Node;

abstract public class Symbol extends SymbolTable {

	String name;

	SymbolTable symbolTable;

	Node astNode;

	UnitSymbol unit;
	
	public UnitSymbol getUnit() {
		return unit;
	}

	public void setUnit(UnitSymbol unit) {
		this.unit = unit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SymbolTable getSymbolTable() {
		return symbolTable;
	}

	public void setSymbolTable(SymbolTable symbolTable) {
		this.symbolTable = symbolTable;
	}

	public String getCannonicalName() {
		if (this instanceof PackageSymbol && getName().equals(""))
			return "";
		String result = ((Symbol)getParent()).getCannonicalName(); 
		if (!result.equals(""))
			result += ".";
		return result + getName();  
	}
	
	public Node getAstNode() {
		return astNode;
	}

	public void setAstNode(Node astNode) {
		this.astNode = astNode;
	}

	public void dump(PrintStream ps) {
		dump(ps, 0);
	}

	public Symbol getRootSymbol() {
		Symbol current = this;
		while (current.getParent() != null)
			current = (Symbol) current.getParent();
		return current;
	}
	
	public PackageSymbol getPackageSymbol() {
		Symbol current = this;
		while (!(current instanceof PackageSymbol))
			current = (Symbol) current.getParent();
		return (PackageSymbol)current;
	}

}
