package metagenerics.symbol.type;

import metagenerics.ast.Node;

public class EnumSymbol extends Type {

	public EnumSymbol(Node astNode) {
		setName(astNode.getName());
		setAstNode(astNode);
	}

}
