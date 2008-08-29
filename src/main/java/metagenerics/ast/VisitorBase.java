package metagenerics.ast;

import metagenerics.ast.common.Modifiers;
import metagenerics.ast.common.Semicolon;
import metagenerics.ast.declarations.AnnotationDeclaration;
import metagenerics.ast.declarations.ClassDeclaration;
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
import metagenerics.exception.NotImplementedException;

public class VisitorBase implements Visitor {

	public void visit(UnitAst unit) {
		throw new NotImplementedException();
	}

	public void visit(PackageDeclaration unitAst) {
		throw new NotImplementedException();
	}

	public void visit(ImportAst importAst) {
		throw new NotImplementedException();
	}

	public void visit(ClassDeclaration classAst) {
		throw new NotImplementedException();
	}

	public void visit(Interface interfaceAst) {
		throw new NotImplementedException();
	}

	public void visit(EnumDeclaration klass) {
		throw new NotImplementedException();
	}

	public void visit(AnnotationDeclaration klass) {
		throw new NotImplementedException();
	}

	public void visit(MetaGenericAst klass) {
		throw new NotImplementedException();
	}

	public void visit(MetaTypedefAst element) {
		throw new NotImplementedException();
	}

	public void visit(VariableBuilder vb) {
		throw new NotImplementedException();
	}

	public void visit(Method method) {
		throw new NotImplementedException();
	}

	public void visit(Constructor constructor) {
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

	public void visit(Modifiers modifiers) {
		throw new NotImplementedException();
	}

}
