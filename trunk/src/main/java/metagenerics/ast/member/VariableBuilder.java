package metagenerics.ast.member;

import java.util.ArrayList;
import java.util.List;

import metagenerics.ast.Visitor;
import metagenerics.ast.common.Annotations;
import metagenerics.ast.common.Modifiers;

import org.antlr.runtime.Token;

public class VariableBuilder extends Member {

	String type;

	List<Field> names = new ArrayList<Field>();

	Modifiers modifiers = new Modifiers();

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Modifiers getModifiers() {
		return modifiers;
	}

	public void setModifiers(Modifiers modifiers) {
		this.modifiers = modifiers;
	}

	public void add(String name, Token start, Token stop, String text) {
		Field field = new Field();
		field.setName(name);
		field.setInfo(start, stop, text);
		names.add(field);
	}


	public List<Field> getFields() {
		for (Field field: names) {
			field.setModifiers(modifiers);
			field.setType(type);
		}
		return names;
	}

	public Annotations getAnnotations() {
		return modifiers.getAnnotations();
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
	
}