package metagenerics.integration;

import static metagenerics.TestHelper.getIntegrationTestFileName;
import static util.FolderUtils.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collections;

import junit.framework.Assert;
import metagenerics.walkers.MetaCompilerWalker;
import metagenerics.walkers.MetaPreCompilerWalker;

import org.junit.Test;

import util.FileByTokenComparator;
import util.FileUtils;
import util.FolderComparator;
import util.SvnFolderFilter;
import util.exception.NotAFileOrFolderException;

public class IntegrationTest {

	String sourceFolder;

	String intermediateFolder;

	String outputFolder;

	String folder;

	protected String runProgram(String folder, String className)
			throws IOException {
		Runtime runtime = Runtime.getRuntime();
		String cmd = "java -cp " + folder + " " + className;
		Process p = runtime.exec(cmd);
		Reader r = new InputStreamReader(p.getInputStream());

		try {
			p.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		char[] buffer = new char[1024];
		int i = r.read(buffer);
		return String.valueOf(buffer, 0, i);
	}

	protected void runCompilerTest() throws IOException {
		MetaCompilerWalker compiler = new MetaCompilerWalker();
		compiler.setSourceFolders(Collections.singletonList(sourceFolder));
		compiler.setIntermediateFolder(intermediateFolder);
		compiler.setTargetFolder(outputFolder);
		compiler.compile();

		String result = runProgram(outputFolder, "Launcher");

		String expectedOutputFileName = getIntegrationTestFileName(folder
				+ "/stdout.txt");
		File stdoutTest = new File(expectedOutputFileName);

		if (stdoutTest.isFile()) {
			String expected = FileUtils.load(expectedOutputFileName);
			if (!result.equals(expected))
				Assert.fail("Expected '" + expected + "', but '" + result
						+ "' found.");
		} else {
			System.out.println(result);
		}

	}

	protected void runPreCompilerTest() throws IOException {
		String expectedFolder = getIntegrationTestFileName(folder + "/out");
		MetaPreCompilerWalker preCompiler = new MetaPreCompilerWalker();
		preCompiler.setSourceFolders(Collections.singletonList(sourceFolder));
		preCompiler.setIntermediateFolder(intermediateFolder);
		preCompiler.compile();

		if (!new File(expectedFolder).isDirectory())
			return;

		FolderComparator comparator = new FolderComparator();

		FileByTokenComparator fileComparator = new FileByTokenComparator();
		comparator.setFolderFilter(new SvnFolderFilter());
		boolean result = comparator.compareFolders(expectedFolder, preCompiler
				.getPrecompiledCodeIntermediateFolder(), fileComparator);
		if (!result) {
			System.err.println(fileComparator.getError());
			Assert.fail(comparator.getErrorMessage());
		}
	}

	private void cleanIntermediateFolder() throws NotAFileOrFolderException {
		if (isFolder(intermediateFolder))
			folderDelete(intermediateFolder);
	}

	protected void runIntegrationTest(String folder) throws IOException {
		this.folder = folder;
		sourceFolder = getIntegrationTestFileName(folder + "/src");
		intermediateFolder = getIntegrationTestFileName(folder + "/int");
		outputFolder = getIntegrationTestFileName(folder + "/bin");
		cleanIntermediateFolder();
		runCompilerTest();
		cleanIntermediateFolder();
		runPreCompilerTest();

	}

	@Test
	public void accessors() throws IOException {
		runIntegrationTest("accessors");
	}

	@Test
	public void accessorsMultipleFiles() throws IOException {
		runIntegrationTest("accessors_multiple_files");
	}

	@Test
	public void listeners() throws IOException {
		runIntegrationTest("listener");
	}

}
