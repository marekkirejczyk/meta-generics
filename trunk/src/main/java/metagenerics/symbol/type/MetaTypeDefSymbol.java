package metagenerics.symbol.type;

import metagenerics.ast.Node;

public class MetaTypeDefSymbol extends Type {

	public MetaTypeDefSymbol(Node astNode) {
		setName(astNode.getName());
		setAstNode(astNode);
	}

}
