package metagenerics.ast.member;

import metagenerics.ast.Node;
import metagenerics.ast.common.Modifiers;

abstract public class Member extends Node {
	Modifiers modifiers = new Modifiers();

	String name;

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
