package metagenerics.symbol.type;

import metagenerics.ast.Node;
import metagenerics.ast.metageneric.MetaGenericAst;

public class MetaGenericSymbol extends Type {

	public MetaGenericSymbol(Node astNode) {
		setName(astNode.getName());
		setAstNode(astNode);
	}

	@Override
	public MetaGenericAst getAstNode() {
		return (MetaGenericAst) super.getAstNode();
	}

}
