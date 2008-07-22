package metagenerics.visitors;

import metagenerics.ast.Visitor;
import metagenerics.ast.declarations.AnnotationDeclaration;
import metagenerics.ast.declarations.ClassDeclaration;
import metagenerics.ast.declarations.Element;
import metagenerics.ast.declarations.EnumDeclaration;
import metagenerics.ast.declarations.Interface;
import metagenerics.ast.declarations.MockElement;
import metagenerics.ast.member.Block;
import metagenerics.ast.member.Field;
import metagenerics.ast.member.MemberMock;
import metagenerics.ast.member.VariableBuilder;
import metagenerics.ast.metageneric.MetaGeneric;
import metagenerics.ast.metageneric.Typedef;
import metagenerics.ast.unit.Unit;
import metagenerics.transform.metatypedef.TypedefTransform;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;



public class TypedefBuilder implements Visitor {


	public void visit(Unit unit) {
		for (Element element : unit.getElements().getElements())
			if (!(element instanceof MockElement))
				element.accept(this);
	}

	public void visit(ClassDeclaration klass) {

	}

	public void visit(EnumDeclaration klass) {

	}

	public void visit(Interface klass) {
		throw new NotImplementedException();
	}

	public void visit(MetaGeneric metaGeneric) {

	}

	public void visit(Typedef typedef) {
		StringBuilder result = new StringBuilder();
		TypedefTransform transform = new TypedefTransform();
		transform.setSymbolTable(typedef.getSymbolTable());
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

	public void visit(MockElement mock) {
		throw new NotImplementedException();		
	}

}
