package metagenerics.ast.declarations;

import java.util.ArrayList;
import java.util.List;

import util.exception.UnexpectedMatch;

import metagenerics.ast.Node;
import metagenerics.ast.common.Annotations;
import metagenerics.ast.common.Modifiers;
import metagenerics.ast.member.Block;
import metagenerics.ast.member.Constructor;
import metagenerics.ast.member.Member;
import metagenerics.ast.member.Members;
import metagenerics.ast.member.VariableBuilder;

abstract public class Element extends Node {

	protected String name;

	protected Modifiers modifiers = new Modifiers();

	
	
	protected Elements elements = new Elements();
	
	protected Members members = new Members();

	protected List<Node> children = new ArrayList<Node>();
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Annotations getAnnotations() {
		return getModifiers().getAnnotations();
	}

	

	public Modifiers getModifiers() {
		return modifiers;
	}

	public void setModifiers(Modifiers modifiers) {
		this.modifiers = modifiers;
	}

	public boolean add(Element element) {
		return elements.add(element);
	}

	public List<AnnotationDeclaration> getAnnotationsDeclarations() {
		return elements.getAnnotationsDeclarations();
	}

	public List<ClassDeclaration> getClasses() {
		return elements.getClasses();
	}

	public List<EnumDeclaration> getEnums() {
		return elements.getEnums();
	}

	public List<Interface> getInterfaces() {
		return elements.getInterfaces();
	}

	public List<Node> getChildren() {
		return children;
	}

	public void setChildren(List<Node> children) {
		this.children = children;
		for (Node node: children)
			if (node instanceof VariableBuilder)
				members.addAll((VariableBuilder)node);
			else if (node instanceof Member) 
				members.add((Member) node);
			else if (node instanceof Element) 
				elements.add((Element) node);
			else if (node instanceof Block)
				members.add((Block) node);
			else
				throw new UnexpectedMatch(node.getClass());
	}

	public List<Constructor> getConstructors() {
		return members.getConstructors();
	}
	
	
}
