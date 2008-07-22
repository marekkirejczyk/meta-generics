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
import metagenerics.symbol.SymbolTable;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class SymbolTableBuilder implements Visitor {

	SymbolTable symbolTable = new SymbolTable();

	public SymbolTable getSymbolTable() {
		return symbolTable;
	}

	public void setSymbolTable(SymbolTable symbolTable) {
		this.symbolTable = symbolTable;
	}

	public void visit(Unit unit) {
		for (Element element : unit.getElements().getElements())
			if (!(element instanceof MockElement))
				element.accept(this);
		unit.setSymbolTable(symbolTable);
	}

	public void visit(ClassDeclaration klass) {
		symbolTable.add(klass);
		klass.setSymbolTable(symbolTable);
	}

	public void visit(EnumDeclaration klass) {
		throw new NotImplementedException();
	}

	public void visit(Interface klass) {
		throw new NotImplementedException();
	}

	public void visit(MetaGeneric klass) {
		symbolTable.add(klass);
		klass.setSymbolTable(symbolTable);
	}

	public void visit(Typedef element) {
		symbolTable.add(element);
		element.setSymbolTable(symbolTable);
	}

	public void visit(AnnotationDeclaration element) {
		//TODO:
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
