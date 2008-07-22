package metagenerics.ast;

import metagenerics.ast.declarations.AnnotationDeclaration;
import metagenerics.ast.declarations.ClassDeclaration;
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

public interface Visitor {

	public void visit(Unit unit);

	public void visit(ClassDeclaration klass);

	public void visit(Interface klass);

	public void visit(EnumDeclaration klass);

	public void visit(AnnotationDeclaration klass);

	public void visit(MetaGeneric klass);

	public void visit(Typedef element);

	
	public void visit(VariableBuilder vb);

	public void visit(Field field);

	public void visit(Block block);

	
	public void visit(MemberMock mock);

	public void visit(MockElement mock);

}
