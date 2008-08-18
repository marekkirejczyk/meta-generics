package metagenerics.visitors;

import java.util.Collection;

import metagenerics.ast.unit.UnitAst;
import metagenerics.symbol.PackageSymbol;

public class SymbolTableBuilder {

	SymbolTableBuildingPhase1 phase1 = new SymbolTableBuildingPhase1();

	SymbolTableBuildingPhase2 phase2 = new SymbolTableBuildingPhase2();

	public PackageSymbol build(Collection<UnitAst> unitAsts) {
		for (UnitAst unitAst : unitAsts)
			phase1.visit(unitAst);

		phase2.setRootPackage(phase1.getRootPackage());

		for (UnitAst unitAst : unitAsts)
			phase2.visit(unitAst);

		return phase2.getRootPackage();
	}
}
