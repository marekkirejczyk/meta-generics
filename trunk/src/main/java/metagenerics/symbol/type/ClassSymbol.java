package metagenerics.symbol.type;

import metagenerics.ast.Node;
import metagenerics.ast.declarations.ClassDeclaration;

public class ClassSymbol extends Type {

	public ClassSymbol(Node astNode) {
		setName(astNode.getName());
		setAstNode(astNode);
	}

	public ClassSymbol(Class klass) {
		setName(klass.getSimpleName());
	}
	
	@Override
	public ClassDeclaration getAstNode() {
		return (ClassDeclaration) super.getAstNode();
	}

}
