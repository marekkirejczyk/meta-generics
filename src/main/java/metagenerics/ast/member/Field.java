package metagenerics.ast.member;

import metagenerics.ast.Visitor;


public class Field extends Member {

	String type;
	
	String rest;
	
	public String getRest() {
		return rest;
	}

	public void setRest(String rest) {
		this.rest = rest;
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

}
