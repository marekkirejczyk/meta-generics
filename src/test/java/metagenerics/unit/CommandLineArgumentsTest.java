package metagenerics.unit;

import junit.framework.Assert;
import metagenerics.cli.MetaCompiler;
import metagenerics.exception.MissingOptionException;
import metagenerics.exception.UnknownOptionException;
import metagenerics.pipe.MetaJavaCompiler;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CommandLineArgumentsTest {

	final static String SINGLE_SRC = "src";

	final static String MULTIPLE_SRC = "src:test:gen";

	final static String INTERMEDIATE = "inter";

	final static String TARGET = "bin";

	MetaCompiler compiler;

	MetaJavaCompiler compilerWalker;

	@Before
	public void createLauncher() {
		compiler = new MetaCompiler();
		compilerWalker = compiler.getCompiler();
	}

	@Test
	public void launcherMissingOptionTest() throws UnknownOptionException {
		try {
			compiler.processArguments(new String[] { "-src", SINGLE_SRC,
					"-intermediate", INTERMEDIATE });
		} catch (MissingOptionException e) {
			return;
		}
		fail();
	}

	@Test
	public void launcherUnknownOptionTest() throws MissingOptionException {
		try {
			compiler.processArguments(new String[] { "-source", SINGLE_SRC,
					"-intermediate", INTERMEDIATE, "-target", TARGET });
		} catch (UnknownOptionException e) {
			return;
		}
		fail();
	}

	@Test
	public void launcherMissingRestTest() throws UnknownOptionException {
		try {
			compiler.processArguments(new String[] { "-src", SINGLE_SRC,
					"-intermediate", INTERMEDIATE, "-target" });
		} catch (MissingOptionException e) {
			return;
		}
		fail();
	}

	@Test
	public void launcherTest() throws MissingOptionException,
			UnknownOptionException {
		compiler.processArguments(new String[] { "-src", SINGLE_SRC,
				"-intermediate", INTERMEDIATE, "-target", TARGET });
		Assert.assertEquals(compilerWalker.getSourceFolders().get(0), SINGLE_SRC);
		Assert.assertEquals(compilerWalker.getIntermediateFolder(), INTERMEDIATE);
		Assert.assertEquals(compilerWalker.getTargetFolder(), TARGET);
	}

	@Test
	public void multipleSourceTest() throws MissingOptionException,
			UnknownOptionException {
		compiler.processArguments(new String[] { "-src", MULTIPLE_SRC,
				"-intermediate", INTERMEDIATE, "-target", TARGET });

		Assert.assertEquals(compilerWalker.getSourceFolders().get(0), "src");
		Assert.assertEquals(compilerWalker.getSourceFolders().get(1), "test");
		Assert.assertEquals(compilerWalker.getSourceFolders().get(2), "gen");
		Assert.assertEquals(compilerWalker.getSourceFolders().size(), 3);
		Assert.assertEquals(compilerWalker.getIntermediateFolder(), INTERMEDIATE);
		Assert.assertEquals(compilerWalker.getTargetFolder(), TARGET);
	}

}
