package metagenerics.ast.declarations;

import java.util.ArrayList;
import java.util.List;

import metagenerics.ast.Node;
import util.CollectionUtils;

public class Elements extends Node {
	protected List<Element> elements = new ArrayList<Element>();

	public Elements() {
		
	}

	public Elements(Elements copy) {
		elements.addAll(copy.elements);
	}

	
	public void add(Element element) {
		elements.add(element);
	}

	public void remove(Element element) {
		elements.remove(element);
	}

	public List<ClassDeclaration> getClasses() {
		return CollectionUtils.filterByClass(elements, ClassDeclaration.class);
	}

	public List<Interface> getInterfaces() {
		return CollectionUtils.filterByClass(elements, Interface.class);
	}

	public List<EnumDeclaration> getEnums() {
		return CollectionUtils.filterByClass(elements, EnumDeclaration.class);
	}

	public List<AnnotationDeclaration> getAnnotationsDeclarations() {
		return CollectionUtils.filterByClass(elements,
				AnnotationDeclaration.class);
	}

	public List<Element> getElements() {
		return elements;
	}

	@Override
	public Elements clone() {
		return new Elements(this);
	}

	
}
