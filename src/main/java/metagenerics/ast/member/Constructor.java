package metagenerics.ast.member;

import metagenerics.ast.Visitor;

public class Constructor extends AbstractMethod {

	protected Constructor(Constructor copy) {
		super(copy);
	}

	public Constructor() {
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	@Override
	public Constructor clone() {
		return new Constructor(this);
	}
	
	

}
