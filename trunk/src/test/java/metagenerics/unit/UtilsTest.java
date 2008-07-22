package metagenerics.unit;

import static metagenerics.TestHelper.getUnitTestFileName;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Comparator;

import org.junit.Test;

import util.FileByTokenComparator;
import util.FileUtils;
import util.FolderComparator;

public class UtilsTest {

	String testString = "This is file load test\nSee if it works";

	static final String FOLDER_1 = getUnitTestFileName("util/folder1");

	static final String FOLDER_2 = getUnitTestFileName("util/folder2");

	static final String FOLDER_3 = getUnitTestFileName("util/folder3");

	static final String FOLDER_4 = getUnitTestFileName("util/folder4");

	@Test
	public void fileLoad() throws IOException {
		assertEquals(testString, FileUtils
				.load(getUnitTestFileName("util/fileload.txt")));
	}

	Comparator<String> fileComparator = new FileByTokenComparator();
	
	@Test
	public void folderCompare() {
	    FolderComparator c = new FolderComparator();
		assertTrue(c.compareFolders(FOLDER_1, FOLDER_1, fileComparator));
		assertTrue(c.compareFolders(FOLDER_1, FOLDER_4, fileComparator));
		assertTrue(c.compareFolders(FOLDER_2, FOLDER_2, fileComparator));
		assertTrue(c.compareFolders(FOLDER_3, FOLDER_3, fileComparator));
		assertFalse(c.compareFolders(FOLDER_1, FOLDER_2, fileComparator));
		assertFalse(c.compareFolders(FOLDER_1, FOLDER_3, fileComparator));
		assertFalse(c.compareFolders(FOLDER_2, FOLDER_3, fileComparator));
		assertFalse(c.compareFolders(FOLDER_2, FOLDER_1, fileComparator));
		assertFalse(c.compareFolders(FOLDER_3, FOLDER_1, fileComparator));
		assertFalse(c.compareFolders(FOLDER_3, FOLDER_2, fileComparator));
	}
}
