package metagenerics.symbol;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SymbolTable {
	private Map<String, Symbol> symbols = new TreeMap<String, Symbol>();

	private SymbolTable parent;
	
	private List<SymbolTable> imports;
	
	String packageNode;

	public boolean containsSymbol(String name) {
		return symbols.containsKey(name);
	}

	public Symbol get(String name) {
		return symbols.get(name);
	}

	public Symbol add(Symbol symbol) {
		return symbols.put(symbol.getName(), symbol);
	}

	public String toString() {
		StringBuilder result = new StringBuilder("[");
		for (String key : symbols.keySet())
			result.append(key + " ,");
		return result + "]";
	}

	public int size() {
		return symbols.size();
	}

}
