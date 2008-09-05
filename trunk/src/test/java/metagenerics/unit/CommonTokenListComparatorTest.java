package metagenerics.unit;

import static metagenerics.TestHelper.getUnitTestFileName;

import java.io.IOException;
import java.util.List;

import metagenerics.pipe.phase1.parse.MetaJavaLexer;

import org.antlr.runtime.CommonToken;
import org.junit.Assert;
import org.junit.Test;

import util.CommonTokenListComparator;

public class CommonTokenListComparatorTest {

	final static String FILE1 = getUnitTestFileName("util/CommonTokenListComparator1.txt");

	final static String FILE2 = getUnitTestFileName("util/CommonTokenListComparator2.txt");

	final static String FILE3 = getUnitTestFileName("util/CommonTokenListComparator3.txt");

	final static String FILE4 = getUnitTestFileName("util/metageneric.out");

	final static String FILE5 = getUnitTestFileName("util/metageneric.java");

	CommonTokenListComparator comparator = new CommonTokenListComparator();

	@Test
	public void simpleTest() throws IOException {
		List<CommonToken> expected = MetaJavaLexer.tokenizeFile(FILE1);
		List<CommonToken> found = MetaJavaLexer.tokenizeFile(FILE2);
		List<CommonToken> diffrent = MetaJavaLexer.tokenizeFile(FILE3);
		Assert.assertEquals(-1, comparator.compare(expected, found));
		Assert.assertEquals(1, comparator.compare(found, expected));
		Assert.assertEquals(0, comparator.compare(expected, expected));
		Assert.assertEquals(1, comparator.compare(found, expected));
		Assert.assertEquals(-1, comparator.compare(expected, diffrent));
	}

	@Test
	public void oneExtraBracketTest() throws IOException {
		List<CommonToken> expected = MetaJavaLexer
				.tokenizeFile(FILE4);
		List<CommonToken> found = MetaJavaLexer
				.tokenizeFile(FILE5);
		Assert.assertEquals(-1, comparator.compare(expected, found));
	}
}
