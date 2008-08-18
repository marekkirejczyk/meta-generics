package metagenerics.unit;

import static util.StringUtils.isWhite;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import metagenerics.ast.unit.UnitAst;
import metagenerics.io.SourcesWalker;
import metagenerics.transform.parse.MetaJavaLexer;
import metagenerics.transform.parse.PrettyPrinter;

import org.antlr.runtime.CommonToken;
import org.junit.Assert;
import org.junit.Test;

import trash.ParseTransformBase;

public class ASTTest {

	String tokenizedFilename;

	String astTrace = "";

	String fileTrace = "";

	private boolean compareToken(CommonToken c, CommonToken d,
			Iterator<CommonToken> astIterator,
			Iterator<CommonToken> fileIterator) {
		astTrace += c.getText() + " ";
		fileTrace += d.getText() + " ";
		if (c.getText().equals(d.getText()))
			return true;
		System.err.println("In AST was \"" + c.getText() + "\"" + " at line "
				+ c.getLine() + " (" + c.getCharPositionInLine() + ")");
		System.err.println("But in file was \"" + d.getText() + "\""
				+ " at line " + d.getLine() + " (" + c.getCharPositionInLine()
				+ ")");

		astTrace += rest(astIterator);
		fileTrace += rest(fileIterator);

		System.err.println(astTrace);
		System.err.println(fileTrace);
		Assert.fail("Error in file " + tokenizedFilename);
		return false;
	}

	private String rest(Iterator<CommonToken> i) {
		String result = "";
		while (i.hasNext())
			result += i.next().getText();
		return result;
	}

	private void compareTokenList(List<CommonToken> ast, List<CommonToken> file) {
		astTrace = "";
		fileTrace = "";
		Iterator<CommonToken> astIterator = ast.iterator();
		Iterator<CommonToken> fileIterator = file.iterator();

		while (astIterator.hasNext() && fileIterator.hasNext()) {
			CommonToken t = astIterator.next();
			CommonToken u = fileIterator.next();
			while (isWhite(t) && astIterator.hasNext())
				t = astIterator.next();
			while (isWhite(u) && fileIterator.hasNext())
				u = fileIterator.next();
			if (!isWhite(t) && !isWhite(u))
				compareToken(t, u, astIterator, fileIterator);

		}

		if (astIterator.hasNext() || fileIterator.hasNext()) {
			String astRest = rest(astIterator);
			String fileRest = rest(fileIterator);
			if (!isWhite(astRest) || !isWhite(fileRest)) {
				if (!astRest.equals(""))
					System.err.println("Missing in file \"" + astRest + "\"");
				if (!fileRest.equals(""))
					System.err.println("Missing in ast \"" + fileRest + "\"");
				System.err.println(astTrace);
				System.err.println(fileTrace);
				Assert.fail("Error in file " + tokenizedFilename
						+ "token list has diffrent length");
			}
		}
	}

	private void doCompile(UnitAst unit, File fileName) throws IOException {
		StringBuilder text = new StringBuilder();
		new PrettyPrinter(text).visit(unit);
		List<CommonToken> fromAST = MetaJavaLexer.tokenizeString(text
				.toString());
		List<CommonToken> fromFile = MetaJavaLexer.tokenizeFile(fileName
				.getPath());
		compareTokenList(fromAST, fromFile);
	}

	ParseTransformBase byTokenComparator = new ParseTransformBase() {
		@Override
		public void compile(UnitAst unit, File fileName) {
			try {
				
				doCompile(unit, fileName);
			} catch (IOException e) {
				e.printStackTrace();
				Assert.fail();
			}
		}
	};

	@Test
	public void tokenComparison() {
		SourcesWalker walker = new SourcesWalker("test/data/unit/parsing/in",
				"test/data/unit/parsing/in", byTokenComparator);
		walker.setFilter(new FilenameFilter() {
			public boolean accept(File dir, String filename) {
				return filename.endsWith(".java");
			}
		});
		walker.walk();
	}

}
