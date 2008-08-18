package metagenerics.ast.common;

import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import metagenerics.ast.Node;

public class Modifiers extends Node {
	List<Modifier> modifiers = new ArrayList<Modifier>();

	Annotations annotations = new Annotations();
	
	public Annotations getAnnotations() {
		return annotations;
	}

	public boolean containsAnnotation(String arg) {
		return annotations.containsAnnotation(arg);
	}

	public List<Modifier> getModifiers() {
		return Collections.unmodifiableList(modifiers);
	}

	public void add(Object modifierOrAnnotation) {
		if (modifierOrAnnotation instanceof Annotation) {
			add((Annotation) modifierOrAnnotation);
		} else if (modifierOrAnnotation instanceof Modifier) {
			add((Modifier) modifierOrAnnotation);
		} else
			throw new RuntimeException();
	}

	public void add(Annotation arg0) {
		annotations.addAnnotation(arg0);
	}

	public void add(Modifier arg) {
		modifiers.add(arg);
	}

	public void add(String arg) {
		modifiers.add(Modifier.valueOf(arg.toUpperCase()));
	}

}