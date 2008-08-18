package metagenerics.symbol.type;

import metagenerics.ast.Node;

public class AnnotationSymbol extends Type {

	public AnnotationSymbol(Node astNode) {
		setName(astNode.getName());
		setAstNode(astNode);
	}

}
