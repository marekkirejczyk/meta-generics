package metagenerics.unit;

import static junit.framework.Assert.*;

import org.junit.Test;

import static util.StringUtils.*;


public class UtilsTest {
	@Test
	public void escapeingTest() {
		assertEquals("ala", escapeCharacters("ala"));  
		assertEquals("\\\"", escapeCharacters("\""));
		assertEquals("\\\"", escapeCharacters("\\\""));
		assertEquals("\\\"\\\"\\\"", escapeCharacters("\"\\\"\""));
	}
}
