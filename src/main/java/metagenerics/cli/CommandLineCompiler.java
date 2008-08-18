package metagenerics.cli;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import metagenerics.exception.MissingOptionException;
import metagenerics.exception.UnknownOptionException;

abstract public class CommandLineCompiler {

	public final static String SOURCE_OPTION = "-src";

	public final static String TARGET_OPTION = "-target";

	public final static String INTERMEDIATE_OPTION = "-intermediate";

	abstract void setSourceFolders(List<String> folders);

	abstract void setIntermediateFolder(String folder);

	abstract void setTargetFolder(String folder);

	abstract List<String> getSourceFolders();

	abstract String getIntermediateFolder();

	abstract String getTargetFolder();

	abstract void compile() throws IOException;

	abstract void printInfo();

	void setSourceFoldersByString(String arg) {
		List<String> sourceFolders = new ArrayList<String>();
		setSourceFolders(Arrays.asList(arg.split(":")));
	}

	public void processArguments(String[] args) throws MissingOptionException,
			UnknownOptionException {
		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			if (arg.equals(CommandLineCompiler.SOURCE_OPTION)) {
				if (i + 1 < args.length)
					setSourceFoldersByString(args[i + 1]);
				else
					throw new MissingOptionException(
							CommandLineCompiler.SOURCE_OPTION);
			} else if (arg.equals(CommandLineCompiler.TARGET_OPTION)) {
				if (i + 1 < args.length)
					setTargetFolder(args[i + 1]);
				else
					throw new MissingOptionException(
							CommandLineCompiler.TARGET_OPTION);

			} else if (arg.equals(CommandLineCompiler.INTERMEDIATE_OPTION)) {
				if (i + 1 < args.length)
					setIntermediateFolder(args[i + 1]);
				else
					throw new MissingOptionException(
							CommandLineCompiler.INTERMEDIATE_OPTION);
			} else if (arg.charAt(0) == '-') {
				throw new UnknownOptionException("Unknown option " + arg);
			}

		}
		if (getSourceFolders() == null || getSourceFolders().size() == 0)
			throw new MissingOptionException(CommandLineCompiler.SOURCE_OPTION);
		if (getIntermediateFolder() == null)
			throw new MissingOptionException(
					CommandLineCompiler.INTERMEDIATE_OPTION);
		if (getTargetFolder() == null)
			throw new MissingOptionException(CommandLineCompiler.TARGET_OPTION);
	}

	public void run(String[] args) {
		try {
			processArguments(args);
			compile();
		} catch (UnknownOptionException e) {
			System.err.println(e.getMessage());
			printInfo();
		} catch (MissingOptionException e) {
			System.err.println("Missing option: " + e.getMessage());
			printInfo();
		} catch (IOException e) {
			System.err.println("Compilation error");
			e.printStackTrace();
		}
	}
}
