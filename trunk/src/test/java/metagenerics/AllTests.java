package metagenerics;

import metagenerics.integration.IntegrationTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value = Suite.class)
@SuiteClasses(value = { UnitTests.class, IntegrationTest.class })
public class AllTests {

}
