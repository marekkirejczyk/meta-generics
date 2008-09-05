package metagenerics.pipe.phase1.symbol;

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
import metagenerics.exception.NotImplementedException;
import metagenerics.symbol.PackageSymbol;
import metagenerics.symbol.Symbol;
import metagenerics.symbol.UnitSymbol;
import metagenerics.symbol.type.AnnotationSymbol;
import metagenerics.symbol.type.ClassSymbol;
import metagenerics.symbol.type.EnumSymbol;
import metagenerics.symbol.type.InterfaceSymbol;
import metagenerics.symbol.type.MetaGenericSymbol;
import metagenerics.symbol.type.MetaTypeDefSymbol;

public class SymbolTableBuildingPhase1 implements Visitor {

	private PackageSymbol rootPackage = new PackageSymbol("");

	private Symbol parentSymbol;

	private UnitSymbol currentUnit;

	public PackageSymbol getRootPackage() {
		return rootPackage;
	}

	public void visit(UnitAst unit) {
		List<String> path = unit.getPackageDeclaration().getPackagePath();
		PackageSymbol currentPackage = rootPackage.getSubPackage(path);

		currentUnit = new UnitSymbol(unit);

		currentPackage.addUnit(currentUnit);
		unit.setSymbol(currentUnit);

		for (ImportAst anImport : unit.getImports()) {
			parentSymbol = currentPackage;
			anImport.accept(this);
		}

		for (Element element : unit.getElements().getElements()) {
			parentSymbol = currentPackage;
			element.accept(this);
		}
	}

	public void visit(PackageDeclaration packageAst) {

	}

	public void visit(ImportAst importAst) {

	}

	public void visit(ClassDeclaration classAst) {
		ClassSymbol classSymbol = new ClassSymbol(classAst);
		classSymbol.setUnit(currentUnit);
		classAst.setSymbol(classSymbol);
		parentSymbol.add(classSymbol);
		classSymbol.setParent(parentSymbol);
	}

	public void visit(EnumDeclaration enumAst) {
		EnumSymbol symbol = new EnumSymbol(enumAst);
		symbol.setUnit(currentUnit);
		enumAst.setSymbol(symbol);
		parentSymbol.add(symbol);
		symbol.setParent(parentSymbol);
	}

	public void visit(Interface interfaceAst) {
		InterfaceSymbol symbol = new InterfaceSymbol(interfaceAst);
		symbol.setUnit(currentUnit);
		interfaceAst.setSymbol(symbol);
		parentSymbol.add(symbol);
		symbol.setParent(parentSymbol);
	}

	public void visit(MetaGenericAst metaGenericAst) {
		MetaGenericSymbol symbol = new MetaGenericSymbol(metaGenericAst);
		symbol.setUnit(currentUnit);
		metaGenericAst.setSymbol(symbol);
		parentSymbol.add(symbol);
		symbol.setParent(parentSymbol);
	}

	public void visit(MetaTypedefAst metaTypedefAst) {
		MetaTypeDefSymbol symbol = new MetaTypeDefSymbol(metaTypedefAst);
		symbol.setUnit(currentUnit);
		metaTypedefAst.setSymbol(symbol);
		parentSymbol.add(symbol);
		symbol.setParent(parentSymbol);
	}

	public void visit(AnnotationDeclaration annotationAst) {
		AnnotationSymbol symbol = new AnnotationSymbol(annotationAst);
		symbol.setUnit(currentUnit);
		annotationAst.setSymbol(symbol);
		parentSymbol.add(symbol);
		symbol.setParent(parentSymbol);
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

	}

	public void visit(Modifiers modifiers) {
		
	}



}
