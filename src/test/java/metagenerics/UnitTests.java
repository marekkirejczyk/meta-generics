package metagenerics;

import metagenerics.integration.IntegrationTest;
import metagenerics.unit.ASTTest;
import metagenerics.unit.CommandLineArgumentsTest;
import metagenerics.unit.CommonTokenListComparatorTest;
import metagenerics.unit.JavaCompilerTest;
import metagenerics.unit.ParseTest;
import metagenerics.unit.SourceListWalkerTest;
import metagenerics.unit.SourceWalkerTest;
import metagenerics.unit.SymbolTableBuilderTest;
import metagenerics.unit.TransformationTest;
import metagenerics.unit.FolderCompareTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value = Suite.class)
@SuiteClasses(value = { ASTTest.class, TransformationTest.class,
		ParseTest.class, SourceWalkerTest.class, FolderCompareTest.class,
		CommonTokenListComparatorTest.class, SymbolTableBuilderTest.class,
		JavaCompilerTest.class,
		SourceListWalkerTest.class, CommandLineArgumentsTest.class })
public class UnitTests {

}
