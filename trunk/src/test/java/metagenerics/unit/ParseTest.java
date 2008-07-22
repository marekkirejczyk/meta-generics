package metagenerics.unit;

import static metagenerics.TestHelper.getUnitTestFileName;

import java.io.File;
import java.io.IOException;

import metagenerics.ast.unit.Unit;
import metagenerics.compile.parse.JavaLexer;
import metagenerics.compile.parse.JavaParser;
import metagenerics.transform.parse.PrettyPrinter;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.junit.Assert;
import org.junit.Test;

public class ParseTest {

	private Unit parseFile(File file) throws IOException, RecognitionException {
		String path = file.getPath();
		JavaLexer lexer = new JavaLexer();
		lexer.setCharStream(new ANTLRFileStream(path));
		CommonTokenStream tokens = new CommonTokenStream();
		tokens.setTokenSource(lexer);
		JavaParser parser = new JavaParser(tokens);
		return parser.compilationUnit().unit;
	}

	@Test
	public void simpleTest() {
		try {
			Unit unit = parseFile(new File(getUnitTestFileName("parsing/in/MetaExample.java")));
			PrettyPrinter printer = new PrettyPrinter(); 
			printer.setAppendable(new StringBuilder());
			printer.visit(unit);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}
