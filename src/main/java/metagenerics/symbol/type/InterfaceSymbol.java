package metagenerics.symbol.type;

import metagenerics.ast.Node;

public class InterfaceSymbol extends Type {

	public InterfaceSymbol(Node astNode) {
		setName(astNode.getName());
		setAstNode(astNode);
	}

}
