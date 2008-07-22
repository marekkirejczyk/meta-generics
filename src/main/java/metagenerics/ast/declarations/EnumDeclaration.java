package metagenerics.ast.declarations;

import java.util.List;

import metagenerics.ast.Visitor;

public class EnumDeclaration extends Element {
	List<String> constants;

	List<String> implementedInterfaces;

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
