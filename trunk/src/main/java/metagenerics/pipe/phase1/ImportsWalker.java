package metagenerics.pipe.phase1;

import metagenerics.ast.UnitAsts;
import metagenerics.ast.declarations.Element;
import metagenerics.ast.metageneric.MetaGenericAst;
import metagenerics.ast.metageneric.MetaTypedefAst;
import metagenerics.ast.unit.UnitAst;
import metagenerics.symbol.UnitSymbol;
import metagenerics.symbol.type.MetaGenericSymbol;
import metagenerics.symbol.type.MetaTypeDefSymbol;

public class ImportsWalker {

	UnitAsts units;

	public ImportsWalker(UnitAsts units) {
		this.units = units;
	}

	public UnitAsts getUnits() {
		return units;
	}

	public void setUnits(UnitAsts units) {
		this.units = units;
	}

	void addAllImports(UnitAst target, UnitSymbol source) {
		if (target == source.getAstNode())
			return;
		target.addAllImports(source.getAstNode().getImports());
	}

	void transformMetaGenericsImports(UnitAst unit) {
		for (Element element : unit.getElements().getElements())
			if (element instanceof MetaTypedefAst) {
				MetaTypedefAst metaTypedefAst = (MetaTypedefAst) element;
				MetaTypeDefSymbol metaTypedefSymbol = metaTypedefAst
						.getSymbol();
				MetaGenericSymbol metaGenericSymbol = metaTypedefSymbol
						.getFunction();
				addAllImports(unit, metaGenericSymbol.getUnit());
			}

	}

	void transformMetaTypedefsImports(UnitAst unit) {
		for (Element element : unit.getElements().getElements())
			if (element instanceof MetaGenericAst)
				unit.addAllImports(MetaGenericAst.getStandardImports());

	}

	public void transformImports() {
		for (UnitAst unit : units.values())
			transformMetaGenericsImports(unit);
		for (UnitAst unit : units.values())
			transformMetaTypedefsImports(unit);
	}

}
