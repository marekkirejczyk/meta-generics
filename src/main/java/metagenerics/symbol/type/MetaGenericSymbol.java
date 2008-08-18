package metagenerics.symbol.type;

import metagenerics.ast.Node;

public class MetaGenericSymbol extends Type {

	public MetaGenericSymbol(Node astNode) {
		setName(astNode.getName());
		setAstNode(astNode);
	}

}
