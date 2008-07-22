package metagenerics.ast.declarations;

import java.util.List;

import metagenerics.ast.Visitor;
import metagenerics.ast.member.Field;
import metagenerics.ast.member.Method;

public class ClassDeclaration extends Element {
	String superClass;

	List<String> implementedInterfaces;

	List<String> genericParameters;

	public String getSuperClass() {
		return superClass;
	}

	public void setSuperClass(String superClass) {
		this.superClass = superClass;
	}

	public List<String> getImplementedInterfaces() {
		return implementedInterfaces;
	}

	public void setImplementedInterfaces(List<String> implementedInterfaces) {
		this.implementedInterfaces = implementedInterfaces;
	}

	public List<String> getGenericParameters() {
		return genericParameters;
	}

	public void setGenericParameters(List<String> genericParameters) {
		this.genericParameters = genericParameters;
	}

	public List<Method> getMethods() {
		return members.getMethods();
	}

	public List<Field> getFields() {
		return members.getFields();
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
