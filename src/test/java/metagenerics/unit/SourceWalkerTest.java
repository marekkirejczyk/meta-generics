package metagenerics.unit;

import static metagenerics.TestHelper.getUnitTestFileName;

import java.io.File;

import metagenerics.io.ExtensionFilter;
import metagenerics.io.FileTransform;
import metagenerics.io.SourcesWalker;

import org.junit.Assert;
import org.junit.Test;

import util.StringUtils;

public class SourceWalkerTest {

	static final String INTERMEDIATE_FOLDER = getUnitTestFileName("parsing/int");

	static final String SOURCE_FOLDER = "src";

	@Test
	public void SuffixTest() {
		Assert.assertEquals("zdala", StringUtils.getSuffix("ala", "alazdala"));
	}

	@Test
	public void SimpleSourceWalkerTest() {
		FileTransform transform = new FileTransform() {
			@Override
			public void compile(File inputFileName, File outputFileName) {
				Assert.assertEquals(StringUtils.getSuffix(SOURCE_FOLDER,
						inputFileName.getPath()), StringUtils.getSuffix(
						INTERMEDIATE_FOLDER, outputFileName.getPath()));
			}
		};
		// TODO: Prepare some test directory for it ;)
		SourcesWalker walker = new SourcesWalker("src",
				INTERMEDIATE_FOLDER, transform);
		walker.setFilter(new ExtensionFilter("java"));
		walker.walk();
	}
	
	
}
