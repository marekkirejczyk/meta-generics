package metagenerics.visitors;

import java.util.ArrayList;
import java.util.List;

import metagenerics.ast.Visitor;
import metagenerics.ast.common.Semicolon;
import metagenerics.ast.declarations.AnnotationDeclaration;
import metagenerics.ast.declarations.ClassDeclaration;
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
import metagenerics.symbol.PackageSymbol;
import metagenerics.symbol.Symbol;
import metagenerics.symbol.UnitSymbol;
import metagenerics.symbol.type.Type;

public class SymbolTableBuildingPhase2 implements Visitor {

	private PackageSymbol rootPackage;

	private UnitSymbol currentUnit;

	public PackageSymbol getRootPackage() {
		return rootPackage;
	}

	public void setRootPackage(PackageSymbol rootPackage) {
		this.rootPackage = rootPackage;
	}

	public void visit(UnitAst unit) {
		currentUnit = (UnitSymbol) unit.getSymbol();
		for (ImportAst anImport : unit.getImports())
			anImport.accept(this);

	}

	public void visit(PackageDeclaration unitAst) {
		// TODO Auto-generated method stub

	}

	public void visit(ImportAst importAst) {

		Symbol symbol = rootPackage.localLookup(importAst.getPath());
		if (symbol == null) {
			System.err.println("null! " + importAst.getIdentifiers());
			return;
			// throw new UnknownSymbolException();
		}
		if (importAst.isStatic()) {

		} else {
			if (importAst.isGeneral() && symbol instanceof PackageSymbol)
				currentUnit.addPackageImport((PackageSymbol) symbol);
			else if (!importAst.isGeneral() && symbol instanceof Type)
				currentUnit.addTypeImport((Type) symbol);
			else
				System.err.println(symbol.getClass().getName() + "! "
						+ importAst.getPath());
		}
	}

	public void visit(ClassDeclaration classAst) {
		// TODO Auto-generated method stub

	}

	public void visit(Interface interfaceAst) {
		// TODO Auto-generated method stub

	}

	public void visit(EnumDeclaration klass) {
		// TODO Auto-generated method stub

	}

	public void visit(AnnotationDeclaration klass) {
		// TODO Auto-generated method stub

	}

	public void visit(MetaGenericAst klass) {
		// TODO Auto-generated method stub

	}

	public void visit(MetaTypedefAst element) {
		// TODO Auto-generated method stub

	}

	public void visit(VariableBuilder vb) {
		// TODO Auto-generated method stub

	}

	public void visit(Field field) {
		// TODO Auto-generated method stub

	}

	public void visit(Block block) {
		// TODO Auto-generated method stub

	}

	public void visit(MemberMock mock) {
		// TODO Auto-generated method stub

	}

	public void visit(Semicolon mock) {
		// TODO Auto-generated method stub

	}

}
