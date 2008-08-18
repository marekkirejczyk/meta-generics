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
import metagenerics.symbol.type.MetaTypeDefSymbol;
import metagenerics.transform.metatypedef.TypedefTransform;
import metagenerics.exception.NotImplementedException;



public class TypedefBuilder implements Visitor {


	public void visit(UnitAst unit) {
		for (Element element : unit.getElements().getElements())
			if (!(element instanceof Semicolon))
				element.accept(this);
	}

	public void visit(PackageDeclaration packageAst) {
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

	public void visit(MetaGenericAst metaGeneric) {

	}

	public void visit(MetaTypedefAst typedef) {
		StringBuilder result = new StringBuilder();
		TypedefTransform transform = new TypedefTransform();
		transform.setMetaTypedefSymbol((MetaTypeDefSymbol)typedef.getSymbol());
		transform.transform(typedef, result);
		typedef.setTextAfterTransformation(result.toString());
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

	public void visit(MemberMock mock) {
		throw new NotImplementedException();		
	}

	public void visit(Semicolon mock) {
		throw new NotImplementedException();		
	}



}
