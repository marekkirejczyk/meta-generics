package metagenerics.ast.declarations;

import metagenerics.ast.Visitor;

public class AnnotationDeclaration extends Element {
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
