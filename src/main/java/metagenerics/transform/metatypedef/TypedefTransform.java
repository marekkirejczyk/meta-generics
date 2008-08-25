package metagenerics.transform.metatypedef;

import metagenerics.ast.declarations.ClassDeclaration;
import metagenerics.ast.metageneric.MetaGenericAst;
import metagenerics.ast.metageneric.MetaTypedefAst;
import metagenerics.runtime.MetaGeneric;
import metagenerics.symbol.Symbol;
import metagenerics.symbol.SymbolTable;
import metagenerics.symbol.type.MetaTypeDefSymbol;

public class TypedefTransform {

	MetaTypeDefSymbol metaTypedefSymbol;

	public void transform(MetaTypedefAst typedef, StringBuilder result) {
		int i = 1;
		String functionName = typedef.getFunction();
		Symbol functionSymbol = metaTypedefSymbol.lookup(functionName);
		MetaGenericAst metaGenericAst = (MetaGenericAst) functionSymbol
				.getAstNode();
		MetaGeneric metaGeneric = metaGenericAst.getMetagenericInstance();
		
		for (String parameter : typedef.getParameters()) {
			Symbol classSymbol = metaTypedefSymbol.lookup(parameter);
			ClassDeclaration cd = (ClassDeclaration) classSymbol.getAstNode();
			metaGeneric.setArgument(i++, cd);
		}
		metaGeneric.generateClass(typedef, result);
	}

	public SymbolTable getMetaTypedefSymbol() {
		return metaTypedefSymbol;
	}

	public void setMetaTypedefSymbol(MetaTypeDefSymbol symbolTable) {
		this.metaTypedefSymbol = symbolTable;
	}

}
