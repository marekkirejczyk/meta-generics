package metagenerics.ast.member;

import metagenerics.ast.Visitor;

public class MemberMock extends Member {

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
