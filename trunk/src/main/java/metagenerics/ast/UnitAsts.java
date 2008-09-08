package metagenerics.ast;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import metagenerics.ast.unit.UnitAst;

public class UnitAsts {

	Map<String, UnitAst> units;

	public void putAll(UnitAsts asts) {
		units.putAll(asts.units);
	}

	public UnitAsts(Map<String, UnitAst> units) {
		this.units = units;
	}

	protected UnitAsts() {

	}

	@Override
	public UnitAsts clone() {
		UnitAsts result = new UnitAsts();
		result.units = new TreeMap<String, UnitAst>();
		for (Map.Entry<String, UnitAst> unit : units.entrySet())
			result.units.put(unit.getKey(), unit.getValue().clone());
		return result;
	}

	public Set<Entry<String, UnitAst>> entrySet() {
		return units.entrySet();
	}

	public Set<String> keySet() {
		return units.keySet();
	}

	public Collection<UnitAst> values() {
		return units.values();
	}

	public UnitAst get(String filename) {
		return units.get(filename);
	}

	public UnitAst put(String arg0, UnitAst arg1) {
		return units.put(arg0, arg1);
	}

}
