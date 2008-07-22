package metagenerics.ast.declarations;

import java.util.List;

import metagenerics.ast.Visitor;
import metagenerics.ast.member.Method;

public class Interface extends Element {

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	List<String> extendedInterfaces;
	
	List<String> genericParameters;
	
	public List<Method> getMethods() {
		return members.getMethods();
	}

}
