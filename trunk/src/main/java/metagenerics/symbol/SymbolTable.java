package metagenerics.symbol;

import static util.PrintStreamUtils.printWithIndents;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import metagenerics.exception.UnknownSymbolException;
import util.CollectionUtils;

abstract public class SymbolTable {
	private Map<String, Symbol> symbols = new TreeMap<String, Symbol>();

	private SymbolTable parent;

	public boolean containsSymbol(String name) {
		return symbols.containsKey(name);
	}

	public Symbol getSymbol(String name) {
		return symbols.get(name);
	}

	public Symbol add(Symbol symbol) {
		return symbols.put(symbol.getName(), symbol);
	}

	public int size() {
		return symbols.size();
	}

	public SymbolTable getParent() {
		return parent;
	}

	public void setParent(SymbolTable parent) {
		this.parent = parent;
	}

	public Map<String, Symbol> getSymbols() {
		return symbols;
	}

	private Symbol localLookup(List<String> path) {
		if (path.size() == 0)
			return (Symbol) this;
		Symbol first = getSymbol(path.get(0));
		if (first == null)
			return null;
		CollectionUtils.removeFirst(path);
		return ((SymbolTable) first).localLookup(path);
	}

	public Symbol localLookup(String name) {
		List<String> path = CollectionUtils.createList(name.split("\\."));
		return localLookup(path);
	}

	public Symbol lookup(String name) {
		Symbol thisSymbol = (Symbol) this;

		/* local == package lookup */
		Symbol symbol = thisSymbol.getPackageSymbol().localLookup(name);
		if (symbol != null)
			return symbol;

		/* import lookup */
		symbol = thisSymbol.getUnit().importsLookup(name);
		if (symbol != null)
			return symbol;

		/* global lookup */
		symbol = thisSymbol.getRootSymbol().localLookup(name);
		if (symbol == null)
			throw new UnknownSymbolException(name);
		return symbol;

	}

	public void dump(PrintStream ps) {
		dump(ps, 0);
	}

	public void dump(PrintStream ps, int n) {
		String className = this.getClass().getSimpleName();
		String name = (((Symbol) this).getName());
		String line = "'" + name + "' => " + className;
		printWithIndents(ps, n, line);
		if (getSymbols().values().size() > 0) {
			ps.println(" {");
			for (Symbol symbol : getSymbols().values())
				symbol.dump(ps, n + 1);
			printWithIndents(ps, n, "}");
		}
		ps.println();
	}
}
