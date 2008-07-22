package metagenerics.ast.metageneric;

import java.util.List;

import metagenerics.ast.Visitor;

public class MetaGeneric extends MetaDeclaration {

	metagenerics.MetaGeneric metagenericInstance;
	
	String textAfterTransformation;
	
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

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public metagenerics.MetaGeneric getMetagenericInstance() {
		return metagenericInstance;
	}

	public void setMetagenericInstance(metagenerics.MetaGeneric metagenericInstance) {
		this.metagenericInstance = metagenericInstance;
	}

	@Override
	public String getText() {
		return getTextAfterTransformation() == null ? super.getText() : getTextAfterTransformation();
	}

	public String getTextAfterTransformation() {
		return textAfterTransformation;
	}

	public void setTextAfterTransformation(String textAfterTransformation) {
		this.textAfterTransformation = textAfterTransformation;
	}

	
	
}
