package metagenerics.visitors;

import java.util.ArrayList;
import java.util.List;

import metagenerics.ast.Visitor;
import metagenerics.ast.common.Modifiers;
import metagenerics.ast.common.Semicolon;
import metagenerics.ast.declarations.AnnotationDeclaration;
import metagenerics.ast.declarations.ClassDeclaration;
import metagenerics.ast.declarations.Element;
import metagenerics.ast.declarations.EnumDeclaration;
import metagenerics.ast.declarations.Interface;
import metagenerics.ast.member.Block;
import metagenerics.ast.member.Constructor;
import metagenerics.ast.member.Field;
import metagenerics.ast.member.MemberMock;
import metagenerics.ast.member.Method;
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
			element.accept(this);

		List<Element> removeList = new ArrayList<Element>();

		for (Element element : unit.getElements().getElements()) {
			if (element instanceof ClassDeclaration) {
				ClassDeclaration cd = (ClassDeclaration) element;
				boolean notNull = cd.getAnnotations() != null;

				if (notNull
						&& cd.getAnnotations().containsAnnotation("Disappear")) {
					removeList.add(cd);
				}
			}
		}
		unit.getElements().getElements().removeAll(removeList);
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
		MetaTypeDefSymbol symbol = (MetaTypeDefSymbol) typedef.getSymbol();
		transform.setMetaTypedefSymbol(symbol);
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
	}

	public void visit(Method method) {
		throw new NotImplementedException();
	}

	public void visit(Constructor constructor) {
		throw new NotImplementedException();
	}

	public void visit(Modifiers modifiers) {
		throw new NotImplementedException();
	}

}
