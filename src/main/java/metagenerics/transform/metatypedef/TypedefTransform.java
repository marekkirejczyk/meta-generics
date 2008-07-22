package metagenerics.transform.metatypedef;

import metagenerics.ast.declarations.ClassDeclaration;
import metagenerics.ast.metageneric.MetaGeneric;
import metagenerics.ast.metageneric.Typedef;
import metagenerics.symbol.SymbolTable;

public class TypedefTransform {

	SymbolTable symbolTable;

	public void transform(Typedef typedef, StringBuilder result) {
		int i = 1;
		MetaGeneric metaGenericAst = (MetaGeneric) symbolTable.get(typedef
				.getFunction());

		metagenerics.MetaGeneric metaGeneric = metaGenericAst
				.getMetagenericInstance();
		for (String parameter : typedef.getParameters()) {
			ClassDeclaration cd = (ClassDeclaration) symbolTable.get(parameter);
			metaGeneric.setArgument(i++, cd);
		}
		metaGeneric.generateClass(typedef, result);
	}

	public SymbolTable getSymbolTable() {
		return symbolTable;
	}

	public void setSymbolTable(SymbolTable symbolTable) {
		this.symbolTable = symbolTable;
	}

}
