package metagenerics.symbol.type;

import metagenerics.ast.Node;
import metagenerics.ast.declarations.ClassDeclaration;

public class ClassSymbol extends Type {

	public ClassSymbol(Node astNode) {
		setName(astNode.getName());
		setAstNode(astNode);
	}

	@Override
	public ClassDeclaration getAstNode() {
		return (ClassDeclaration) super.getAstNode();
	}

}
