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

public interface Visitor {

	public void visit(UnitAst unit);

	public void visit(PackageDeclaration unitAst);

	public void visit(ImportAst importAst);

	
	public void visit(ClassDeclaration classAst);

	public void visit(Interface interfaceAst);

	public void visit(EnumDeclaration klass);

	public void visit(AnnotationDeclaration klass);

	
	public void visit(MetaGenericAst klass);

	public void visit(MetaTypedefAst element);

	
	public void visit(VariableBuilder vb);
	
	public void visit(Method method);
	
	public void visit(Constructor constructor);

	public void visit(Field field);

	public void visit(Block block);

	public void visit(MemberMock mock);

	public void visit(Semicolon mock);

	
	public void visit(Modifiers modifiers);
}
