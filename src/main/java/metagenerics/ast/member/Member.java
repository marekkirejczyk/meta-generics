package metagenerics.ast.member;

import metagenerics.ast.common.Modifiers;
import metagenerics.ast.declarations.Element;

abstract public class Member extends Element {
	Modifiers modifiers = new Modifiers();

	String name;

	protected Member(Member copy) {
		setModifiers(copy.getModifiers());
	}
	
	protected Member() {
		
	}

	public Modifiers getModifiers() {
		return modifiers;
	}

	public void setModifiers(Modifiers modifiers) {
		this.modifiers = modifiers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean hasAnnotation(String arg) {
		return modifiers.containsAnnotation(arg);
	}

}
