package metagenerics.unit;

import org.junit.Test;


public class JarToolsTest {
	@Test
	public void test() {
		//java.ext.dirs
		String dirs = System.getProperty("java.class.path");
		for (String s: dirs.split(":"))
			System.out.println(s);
		System.out.println(System.getProperty("java.home"));
	}
}
