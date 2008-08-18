package metagenerics.unit;

import static metagenerics.TestHelper.getUnitTestFileName;
import metagenerics.ast.unit.UnitAst;
import metagenerics.transform.parse.MetaJavaParser;
import metagenerics.transform.parse.PrettyPrinter;

import org.junit.Assert;
import org.junit.Test;

public class ParseTest {

	MetaJavaParser parser = new MetaJavaParser();

	@Test
	public void simpleTest() {
		try {
			String filename = getUnitTestFileName("parsing/in/MetaExample.java");
			UnitAst unit = parser.parse(filename);
			PrettyPrinter printer = new PrettyPrinter();
			printer.setAppendable(new StringBuilder());
			printer.visit(unit);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}
