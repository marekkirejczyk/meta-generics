package metagenerics.ast.common;

import metagenerics.ast.Visitor;
import metagenerics.ast.declarations.Element;

public class Semicolon extends Element {

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
