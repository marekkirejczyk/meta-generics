package metagenerics.visitors;

import java.util.Map;

import metagenerics.ast.VisitorBase;
import metagenerics.ast.declarations.AnnotationDeclaration;
import metagenerics.ast.declarations.ClassDeclaration;
import metagenerics.ast.declarations.Element;
import metagenerics.ast.declarations.EnumDeclaration;
import metagenerics.ast.declarations.Interface;
import metagenerics.ast.metageneric.MetaGenericAst;
import metagenerics.ast.metageneric.MetaTypedefAst;
import metagenerics.ast.unit.UnitAst;
import metagenerics.transform.metageneric.MetaGenericCompiler;

public class MetaGenericCompilerBuilder extends VisitorBase {

	MetaGenericCompiler compiler = new MetaGenericCompiler();

	public MetaGenericCompilerBuilder(String intermediateFolder) {
		setIntermediateFolder(intermediateFolder);
	}

	public String getIntermediateFolder() {
		return compiler.getIntermediateFolder();
	}

	public void setIntermediateFolder(String intermediateFolder) {
		compiler.setIntermediateFolder(intermediateFolder);
	}

	public Map<String, UnitAst> build(Map<String, UnitAst> units) {
		for (UnitAst unit : units.values())
			unit.accept(this);
		return units;
	}

	public void visit(UnitAst unit) {
		for (Element element : unit.getElements().getElements())
			element.accept(this);
	}

	@Override
	public void visit(AnnotationDeclaration annotation) {

	}

	@Override
	public void visit(ClassDeclaration classAst) {

	}

	@Override
	public void visit(EnumDeclaration klass) {

	}

	@Override
	public void visit(Interface interfaceAst) {

	}

	@Override
	public void visit(MetaGenericAst metaGenericAst) {
		metaGenericAst.setMetagenericInstance(compiler.compile(metaGenericAst));
	}

	@Override
	public void visit(MetaTypedefAst element) {

	}

}
