package metagenerics.integration;

import static metagenerics.TestHelper.getIntegrationTestFileName;
import static util.FolderUtils.folderDelete;
import static util.FolderUtils.isFolder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.util.Collections;

import junit.framework.Assert;
import metagenerics.pipe.MetaJavaCompiler;
import metagenerics.pipe.MetaJavaPreCompiler;
import util.FileByTokenComparator;
import util.FileUtils;
import util.FolderComparator;
import util.SvnFolderFilter;
import util.exception.NotAFileOrFolderException;

public class MetaProgramUnderTest {

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
		return i < 0 ? "" : String.valueOf(buffer, 0, i);
	}

	protected void runCompilerTest() throws IOException {
		MetaJavaCompiler compiler = new MetaJavaCompiler();
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
		if (!new File(expectedFolder).isDirectory())
			return;
		cleanFolder(intermediateFolder);
		MetaJavaPreCompiler preCompiler = new MetaJavaPreCompiler();
		preCompiler.setSourceFolders(Collections.singletonList(sourceFolder));
		preCompiler.setIntermediateFolder(intermediateFolder);
		preCompiler.compile();

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

	private void cleanFolder(String folder) throws NotAFileOrFolderException {
		if (isFolder(folder))
			folderDelete(folder);
	}

	protected void setupFolders(String folder) {
		this.folder = folder;
		sourceFolder = getIntegrationTestFileName(folder + "/src");
		intermediateFolder = getIntegrationTestFileName(folder + "/int");
		outputFolder = getIntegrationTestFileName(folder + "/bin");
	}

	protected void runIntegrationTest(String folder) throws IOException {
		setupFolders(folder);
		cleanFolder(intermediateFolder);
		cleanFolder(outputFolder);
		runCompilerTest();
		runPreCompilerTest();
	}

	class Copier implements Runnable {

		Reader is;

		PrintStream os;

		public Copier(InputStream is, PrintStream os) {
			this.is = new InputStreamReader(is);
			this.os = os;
		}

		public void doRun() throws IOException {
			char[] buffer = new char[1024];
			int n;
			do {
				n = is.read(buffer);
				for (int i = 0; i < n; i++)
					os.write(buffer[i]);
			} while (n > 0);
		}

		public void run() {
			try {
				doRun();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void runGuiTest(String folder) throws IOException {
		setupFolders(folder);
		cleanFolder(intermediateFolder);
		MetaJavaCompiler compiler = new MetaJavaCompiler();
		compiler.setSourceFolders(Collections.singletonList(sourceFolder));
		compiler.setIntermediateFolder(intermediateFolder);
		compiler.setTargetFolder(outputFolder);
		compiler.compile();

		Runtime runtime = Runtime.getRuntime();
		String cmd = "java -cp " + outputFolder + " " + "GuiLauncher";
		Process p = runtime.exec(cmd);

		new Thread(new Copier(p.getInputStream(), System.out)).start();
		new Thread(new Copier(p.getErrorStream(), System.err)).start();

	}
}
