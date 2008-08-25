package metagenerics.ast.member;

import metagenerics.ast.Visitor;

public class Method extends AbstractMethod {

	String type;

	protected Method(Method copy) {
		super(copy);
		setType(copy.getType());
	}
	
	public Method() {
		
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	@Override
	public Method clone() {
		return new Method(this);
	}

}
