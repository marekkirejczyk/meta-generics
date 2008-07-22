package metagenerics.unit;

import static metagenerics.TestHelper.getIntegrationTestFileName;
import static metagenerics.TestHelper.getUnitTestFileName;
import metagenerics.TestHelper;
import metagenerics.ast.metageneric.MetaGeneric;
import metagenerics.ast.metageneric.Typedef;
import metagenerics.ast.unit.Unit;
import metagenerics.symbol.SymbolTable;
import metagenerics.transform.parse.MetaJavaParser;
import metagenerics.transform.parse.PrettyPrinter;
import metagenerics.visitors.MetaGenericBuilder;
import metagenerics.visitors.SymbolTableBuilder;
import metagenerics.visitors.TypedefBuilder;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class SymbolTableBuilderTest {

	final static String SOURCE_FILE_NAME = getIntegrationTestFileName("accessors/in/Person.java");
	
	final static String PRECOMPILED_FILE_NAME = getIntegrationTestFileName("accessors/out/Person.java");

	final static String INTERMEDIATE_FOLDER = getUnitTestFileName("MetaGenericTransform/int");

	SymbolTableBuilder symbolBuilder = new SymbolTableBuilder();

	MetaGenericBuilder genericBuilder = new MetaGenericBuilder();

	MetaJavaParser parser = new MetaJavaParser();
	
	TypedefBuilder typedefBuilder = new TypedefBuilder();

	@Test
	public void simpleTest() throws Throwable {
		Unit unit = parser.parse(SOURCE_FILE_NAME);
		symbolBuilder.visit(unit);

		SymbolTable symbolTable = unit.getSymbolTable();
		Assert.assertTrue(symbolTable.containsSymbol("Adress"));
		Assert.assertTrue(symbolTable.containsSymbol("PersonStub"));
		Assert.assertTrue(symbolTable.containsSymbol("AddAccessors"));
		Assert.assertTrue(symbolTable.containsSymbol("Person"));
		Assert.assertEquals(4, symbolBuilder.getSymbolTable().size());

		genericBuilder.setIntermediateFolder(INTERMEDIATE_FOLDER);
		genericBuilder.visit(unit);
		MetaGeneric ast = (MetaGeneric) symbolTable.get("AddAccessors");
		metagenerics.MetaGeneric metaGeneric = ast.getMetagenericInstance();
		Assert.assertNotNull(metaGeneric);

		typedefBuilder.visit(unit);
		Typedef typedfAst = (Typedef) symbolTable.get("Person");
		
		Assert.assertNotNull(typedfAst.getTextAfterTransformation());
		
		StringBuilder result = new StringBuilder();
		
		PrettyPrinter printer = new PrettyPrinter(result);
		printer.visit(unit);
		
		TestHelper.assertEqualsFileAndStringTokens(PRECOMPILED_FILE_NAME, result.toString());
	}

	@Ignore
	@Test
	public void fullGrammarTest() {
		// TODO:
	}
}
