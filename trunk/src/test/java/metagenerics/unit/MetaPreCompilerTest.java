package metagenerics.unit;

import static metagenerics.TestHelper.getIntegrationTestFileName;

import java.io.IOException;
import java.util.Collections;

import metagenerics.walkers.MetaPreCompiler;

import org.junit.Assert;
import org.junit.Test;

import util.FileByTokenComparator;
import util.FolderComparator;

public class MetaPreCompilerTest {

	void integrationIntermediateTest(String folderName) throws IOException {
		String sourceFolder = getIntegrationTestFileName(folderName + "/in/");
		String intermediateFolder = getIntegrationTestFileName(folderName
				+ "/int/");
		String outputFolder = getIntegrationTestFileName(folderName + "/out/");

		MetaPreCompiler preCompiler = new MetaPreCompiler();
		preCompiler.setSourceFolders(Collections.singletonList(sourceFolder));
		preCompiler.setIntermediateFolder(intermediateFolder);
		preCompiler.compile();
		FolderComparator comparator = new FolderComparator();

		FileByTokenComparator fileComparator = new FileByTokenComparator();
		boolean result = comparator.compareFolders(outputFolder, preCompiler
				.getPrecompiledCodeIntermediateFolder(), fileComparator);
		if (!result) {
			System.err.println(comparator.getErrorMessage());
			System.err.println(fileComparator.getError());
			Assert.fail();
		}
	}

	@Test
	public void testAccessors() throws IOException {
		integrationIntermediateTest("accessors");

	}

	@Test
	public void testAccessorsWithMultipleFiles() throws IOException {
		integrationIntermediateTest("accessors_multiple_files");
	}

	@Test
	public void testListener() throws IOException {
		integrationIntermediateTest("listener");
	}

}
