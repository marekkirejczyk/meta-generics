package metagenerics.unit;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static metagenerics.TestHelper.getIntegrationTestFileName;

import java.util.Map;

import junit.framework.Assert;

import metagenerics.ast.metageneric.MetaTypedefAst;
import metagenerics.ast.unit.UnitAst;
import metagenerics.symbol.PackageSymbol;
import metagenerics.symbol.Symbol;
import metagenerics.symbol.SymbolTable;
import metagenerics.symbol.UnitSymbol;
import metagenerics.symbol.type.ClassSymbol;
import metagenerics.symbol.type.MetaGenericSymbol;
import metagenerics.symbol.type.MetaTypeDefSymbol;
import metagenerics.transform.parse.PrettyPrinter;
import metagenerics.visitors.MetaGenericBuilder;
import metagenerics.visitors.SymbolTableBuilder;
import metagenerics.visitors.TypedefBuilder;
import metagenerics.walkers.ParseWalker;

import org.junit.Ignore;
import org.junit.Test;

public class SymbolTableBuilderTest {

	final static String SOURCE_FOLDER = getIntegrationTestFileName("accessors_multiple_files/src");

	// final static String PRECOMPILED_FILE_NAME =
	// getIntegrationTestFileName("accessors/out/Launcher.java");

	// final static String INTERMEDIATE_FOLDER =
	// getUnitTestFileName("MetaGenericTransform/int");

	SymbolTableBuilder symbolBuilder = new SymbolTableBuilder();

	MetaGenericBuilder genericBuilder = new MetaGenericBuilder();

	ParseWalker parser = new ParseWalker();

	TypedefBuilder typedefBuilder = new TypedefBuilder();

	@Test
	public void accessorsTest() {
		parser.parseFolder(SOURCE_FOLDER);
		SymbolTable rootPackage = symbolBuilder.build(parser.getUnits());

		assertEquals(rootPackage.lookup("framework").getClass(),
				PackageSymbol.class);
		assertEquals(rootPackage.getSymbol("model").getClass(),
				PackageSymbol.class);
		assertEquals(rootPackage.lookup("model").getClass(),
				PackageSymbol.class);
		assertEquals(rootPackage.getSymbol("stub").getClass(),
				PackageSymbol.class);
		assertEquals(rootPackage.lookup("stub").getClass(), PackageSymbol.class);
		assertEquals(rootPackage.getSymbol("Launcher").getClass(),
				ClassSymbol.class);
		assertEquals(rootPackage.lookup("Launcher").getClass(),
				ClassSymbol.class);
		assertEquals(rootPackage.lookup("stub").getParent(), rootPackage);
		assertEquals(rootPackage.lookup("stub.PersonStub").getClass(),
				ClassSymbol.class);
		assertEquals(rootPackage.lookup("stub.PersonStub").getClass(),
				ClassSymbol.class);
		assertEquals(
				rootPackage.lookup("stub").lookup("PersonStub").getClass(),
				ClassSymbol.class);
		assertEquals(rootPackage.lookup("model").lookup("Person").getClass(),
				MetaTypeDefSymbol.class);
		Symbol symbol = rootPackage.lookup("model").lookup("Person");
		MetaTypeDefSymbol metaTypedefSymbol = (MetaTypeDefSymbol) symbol;
		assertTrue(symbol.getAstNode() != null);

		MetaTypedefAst ast = (MetaTypedefAst) metaTypedefSymbol.getAstNode();
		assertEquals(ast.getFunction(), "AddAccessors");
		assertEquals(ast.getParameters().size(), 1);
		assertEquals(ast.getParameters().get(0), "PersonStub");

		symbol = rootPackage.lookup("framework.AddAccessors");
		assertTrue(symbol.getAstNode() != null);

		ClassSymbol classSymbol = (ClassSymbol) rootPackage
				.lookup("stub.PersonStub");

		UnitSymbol unit = classSymbol.getUnit();

		assertEquals(1, unit.getTypeImports().size());
		assertEquals(1, unit.getPackageImports().size());
		assertEquals(unit.getTypeImports().get(0), rootPackage
				.lookup("model.Adress"));
		assertEquals(unit.getPackageImports().get(0), rootPackage
				.lookup("framework"));

		MetaTypeDefSymbol typedefSymbol = (MetaTypeDefSymbol) rootPackage
				.lookup("model.Person");

		//PackageSymbol packageSymbol= (PackageSymbol)typedefSymbol.getUnit().getParent();
		//Assert.assertNotNull(packageSymbol);

	}

	@Ignore
	@Test
	public void simpleTest() {
		parser.parseFolder(SOURCE_FOLDER);
		Map<String, UnitAst> units = parser.getUnitsWithNames();
		PackageSymbol packageSymbol = symbolBuilder.build(parser.getUnits());

		SymbolTable symbolTable = units.get("/Launcher.java").getSymbol();
		assertTrue(symbolTable.containsSymbol("Adress"));
		assertTrue(symbolTable.containsSymbol("PersonStub"));
		assertTrue(symbolTable.containsSymbol("AddAccessors"));
		assertTrue(symbolTable.containsSymbol("Person"));
		assertEquals(5, packageSymbol.size());
		/*
		 * genericBuilder.setIntermediateFolder(INTERMEDIATE_FOLDER);
		 * genericBuilder.visit(unit); MetaGenericAst ast = (MetaGenericAst)
		 * symbolTable.get("AddAccessors"); metagenerics.runtime.MetaGeneric
		 * metaGeneric = ast.getMetagenericInstance();
		 * Assert.assertNotNull(metaGeneric);
		 * 
		 * typedefBuilder.visit(unit); MetaTypedefAst typedfAst =
		 * (MetaTypedefAst) symbolTable.get("Person");
		 * 
		 * Assert.assertNotNull(typedfAst.getTextAfterTransformation());
		 * 
		 * StringBuilder result = new StringBuilder();
		 * 
		 * PrettyPrinter printer = new PrettyPrinter(result);
		 * printer.visit(unit);
		 * 
		 * TestHelper.assertEqualsFileAndStringTokens(PRECOMPILED_FILE_NAME,
		 * result.toString());
		 */
	}

}
