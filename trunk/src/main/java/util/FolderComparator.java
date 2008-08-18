package util;

import static java.lang.String.format;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import util.exception.UnexpectedMatch;

public class FolderComparator {

	Filter<String> folderFilter = new Filter<String>() {
		public boolean accept(String t) {
			return true;
		}
	};

	public Filter<String> getFolderFilter() {
		return folderFilter;
	}

	public void setFolderFilter(Filter<String> folderFilter) {
		this.folderFilter = folderFilter;
	}

	public Set<String> listAllFiles(String filename) {
		File file = new File(filename);
		if (file.isFile())
			return Collections.singleton(filename);
		else if (file.isDirectory()) {
			if (!folderFilter.accept(file.getName()))
				return Collections.EMPTY_SET;
			Set<String> result = new TreeSet<String>();
			for (File element : file.listFiles()) {
				result.addAll(listAllFiles(element.getPath()));
			}
			return result;
		} else {
			System.err.println("File not found: " + filename);
			throw new UnexpectedMatch(String.class);
		}

	}

	public boolean compareFolders(String expected, String actual) {
		return compareFolders(expected, actual, new FileByTokenComparator());
	}

	String errorMessage;

	public boolean compareFolders(String expected, String actual,
			Comparator<String> fileComparator) {
		Set<String> expectedSet = listAllFiles(expected);
		Set<String> actualSet = listAllFiles(actual);

		Iterator<String> iterator = actualSet.iterator();
		for (String expectedFileName : expectedSet) {

			if (!iterator.hasNext()) {
				errorMessage = format(
						"File '%1$s' expected, but no more files found",
						expectedFileName);
				return false;
			}

			String actualFileName = iterator.next();

			String expectedShort = PathUtils.pathDiff(expectedFileName,
					expected);
			String actualShort = PathUtils.pathDiff(actualFileName, actual);

			if (!expectedShort.equals(actualShort)) {
				errorMessage = format("'%1$s' expected, but '%2$s' found\n",
						expectedShort, actualShort);
				return false;
			}

			if (fileComparator.compare(expectedFileName, actualFileName) != 0) {
				errorMessage = format("File '%1$s' differs from '%2$s'\n",
						expectedShort, actualShort);
				return false;
			}

		}
		if (iterator.hasNext()) {
			errorMessage = format("No file expected, but found: %1$s", iterator
					.next());
			return false;
		}
		return true;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
