package metagenerics.ast.declarations;

import java.util.ArrayList;
import java.util.List;

import util.CollectionUtils;

public class Elements extends metagenerics.ast.Node {
	protected List<Element> elements = new ArrayList<Element>();

	public boolean add(Element element) {
		return elements.add(element);
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
	
	

}
