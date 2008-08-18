package metagenerics.unit;

import metagenerics.TestHelper;
import metagenerics.walkers.SourceListWalker;

import org.junit.Assert;
import org.junit.Test;


public class SourceListWalkerTest {
	@Test
	public void simpleTest() {
		SourceListWalker walker = new SourceListWalker();
		walker.walk(TestHelper.getIntegrationTestFileName("accessors_multiple_files/src"));
		Assert.assertEquals(8, walker.getList().size());
	}
}
