package metagenerics.ast.declarations;

import java.util.List;

import metagenerics.ast.member.Field;
import metagenerics.ast.member.Method;

abstract public class AbstractClass extends Element {

	public List<Field> getFields() {
		return members.getFields();
	}

	public List<Method> getMethods() {
		return members.getMethods();
	}

}
