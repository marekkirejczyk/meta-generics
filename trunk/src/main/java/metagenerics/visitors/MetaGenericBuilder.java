package metagenerics.visitors;

import metagenerics.ast.Visitor;
import metagenerics.ast.common.Semicolon;
import metagenerics.ast.declarations.AnnotationDeclaration;
import metagenerics.ast.declarations.ClassDeclaration;
import metagenerics.ast.declarations.Element;
import metagenerics.ast.declarations.EnumDeclaration;
import metagenerics.ast.declarations.Interface;
import metagenerics.ast.member.Block;
import metagenerics.ast.member.Field;
import metagenerics.ast.member.MemberMock;
import metagenerics.ast.member.VariableBuilder;
import metagenerics.ast.metageneric.MetaGenericAst;
import metagenerics.ast.metageneric.MetaTypedefAst;
import metagenerics.ast.unit.ImportAst;
import metagenerics.ast.unit.PackageDeclaration;
import metagenerics.ast.unit.UnitAst;
import metagenerics.transform.metageneric.MetaGenericCompiler;
import metagenerics.transform.metageneric.MetaGenericTransform;
import metagenerics.exception.NotImplementedException;

public class MetaGenericBuilder implements Visitor {

	MetaGenericCompiler compiler = new MetaGenericCompiler();

	MetaGenericTransform transfom = new MetaGenericTransform();

	public void visit(UnitAst unit) {
		for (Element element : unit.getElements().getElements())
			if (!(element instanceof Semicolon))
				element.accept(this);
	}
	
	public void visit(PackageDeclaration unit) {
		throw new NotImplementedException();
	}
	
	public void visit(ImportAst importAst) {
		throw new NotImplementedException();
	}

	
	public void visit(ClassDeclaration klass) {

	}

	public void visit(EnumDeclaration klass) {

	}

	public void visit(Interface klass) {
		throw new NotImplementedException();
	}

	public void visit(MetaGenericAst metaGenericAst) {
		StringBuilder result = new StringBuilder();
		//transfom.setUseOriginalModifiers(false);
		transfom.transform(metaGenericAst, result);
		metaGenericAst.setTextAfterTransformation(result.toString());
		metaGenericAst.setMetagenericInstance(compiler.compile(metaGenericAst));
	}

	public void visit(MetaTypedefAst element) {

	}

	public void visit(AnnotationDeclaration klass) {

	}

	public void visit(VariableBuilder vb) {
		throw new NotImplementedException();
	}

	public void visit(Field field) {
		throw new NotImplementedException();
	}

	public void visit(Block block) {
		throw new NotImplementedException();
	}

	public String getIntermediateFolder() {
		return compiler.getIntermediateFolder();
	}

	public void setIntermediateFolder(String intermediateFolder) {
		compiler.setIntermediateFolder(intermediateFolder);
	}

	public void visit(MemberMock mock) {
		throw new NotImplementedException();
	}

	public void visit(Semicolon mock) {
		throw new NotImplementedException();
	}





}
