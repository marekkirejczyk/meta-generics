package metagenerics.ast.declarations;

import java.util.List;

import metagenerics.ast.member.Constructor;

abstract public class AbstractConstructableClass extends AbstractClass {

	public List<Constructor> getConstructors() {
		return members.getConstructors();
	}

}
