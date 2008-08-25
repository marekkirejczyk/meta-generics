package metagenerics.ast.metageneric;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import metagenerics.ast.Visitor;
import metagenerics.ast.unit.ImportAst;
import metagenerics.ast.unit.UnitAst;
import metagenerics.runtime.MetaGeneric;

public class MetaGenericAst extends MetaDeclaration {

	MetaGeneric metagenericInstance;

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

	public metagenerics.runtime.MetaGeneric getMetagenericInstance() {
		return metagenericInstance;
	}

	public void setMetagenericInstance(
			metagenerics.runtime.MetaGeneric metagenericInstance) {
		this.metagenericInstance = metagenericInstance;
	}

	@Override
	public String getText() {
		return getTextAfterTransformation() == null ? super.getText()
				: getTextAfterTransformation();
	}

	public String getTextAfterTransformation() {
		return textAfterTransformation;
	}

	public void setTextAfterTransformation(String textAfterTransformation) {
		this.textAfterTransformation = textAfterTransformation;
	}

	public List<ImportAst> getUnitImports() {
		try {
			UnitAst unitAst = (UnitAst) getSymbol().getUnit().getAstNode();
			return unitAst.getImports();
		} catch (NullPointerException e) {
			// TODO: this is only for testing -> remove
			return Collections.EMPTY_LIST;
		}
	}

	public List<ImportAst> getImports() {
		List<ImportAst> imports = new ArrayList<ImportAst>();
		imports.addAll(getStandardImports());
		imports.addAll(getUnitImports());
		return imports;
	}

	static public List<ImportAst> getStandardImports() {
		List<ImportAst> imports = new ArrayList<ImportAst>();
		imports.add(new ImportAst("metagenerics.ast", true));
		imports.add(new ImportAst("metagenerics.ast.declarations", true));
		imports.add(new ImportAst("metagenerics.ast.member", true));
		imports.add(new ImportAst("metagenerics.ast.common", true));
		return imports;
	}

}
