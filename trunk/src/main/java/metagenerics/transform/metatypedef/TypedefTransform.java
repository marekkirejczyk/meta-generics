package metagenerics.transform.metatypedef;

import metagenerics.ast.metageneric.MetaGenericAst;
import metagenerics.ast.metageneric.MetaTypedefAst;
import metagenerics.runtime.MetaGeneric;
import metagenerics.symbol.type.ClassSymbol;
import metagenerics.symbol.type.MetaGenericSymbol;
import metagenerics.symbol.type.MetaTypeDefSymbol;

public class TypedefTransform {

	public void transform(MetaTypedefAst typedef, StringBuilder result) {
		int i = 1;
		MetaTypeDefSymbol metaTypeDefSymbol = typedef.getSymbol();
		MetaGenericSymbol metaGenericSymbol = metaTypeDefSymbol.getFunction();
		MetaGenericAst metaGenericAst = metaGenericSymbol.getAstNode();
		MetaGeneric metaGeneric = metaGenericAst.getMetagenericInstance();

		for (ClassSymbol classSymbol : metaTypeDefSymbol.getArguments())
			metaGeneric.setArgument(i++, classSymbol.getAstNode());

		metaGeneric.generateClass(typedef, result);
	}

}
