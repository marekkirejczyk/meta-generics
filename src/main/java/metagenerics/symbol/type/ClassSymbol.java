package metagenerics.symbol.type;

import metagenerics.ast.Node;

public class ClassSymbol extends Type {

	public ClassSymbol(Node astNode) {
		setName(astNode.getName());
		setAstNode(astNode);
	}

}
