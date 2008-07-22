package metagenerics;

import java.io.IOException;
import java.util.List;

import metagenerics.ast.unit.Unit;
import metagenerics.transform.parse.MetaJavaLexer;
import metagenerics.transform.parse.MetaJavaParser;

import org.antlr.runtime.CommonToken;
import org.antlr.runtime.RecognitionException;
import org.junit.Assert;

import util.CommonTokenListComparator;

public class TestHelper {
	private static final String UNIT_TEST_DATA_FOLDER_PATH = "test/data/unit/";

	private static final String INTEGRATION_TEST_DATA_FOLDER_PATH = "test/data/integration/";
	
	static public String getUnitTestFileName(String filename) {
		return UNIT_TEST_DATA_FOLDER_PATH + filename;
	}

	static public String getIntegrationTestFileName(String filename) {
		return INTEGRATION_TEST_DATA_FOLDER_PATH + filename;
	}
	
	static public void assertEqualsFileAndStringTokens(String expectedFilename,
			String actuallyString) throws IOException {
		CommonTokenListComparator comparator = new CommonTokenListComparator();
		List<CommonToken> expectedTokens = MetaJavaLexer
				.tokenizeFile(expectedFilename);
		List<CommonToken> actuallyTokens = MetaJavaLexer
				.tokenizeString(actuallyString.toString());

		int same = comparator.compare(expectedTokens, actuallyTokens);
		if (same != 0) {
			System.err.println("Not equal with file " + expectedFilename + ": " + comparator.getError());
			Assert.fail();
		}
	}

	public static <T> T firstASTInFile(Class<T> t, String x) throws IOException, RecognitionException {
		MetaJavaParser parser = new MetaJavaParser();
		Unit unitAST = parser.parse(x);
		return (T)unitAST.getElements().getElements().get(0);
	}
}
