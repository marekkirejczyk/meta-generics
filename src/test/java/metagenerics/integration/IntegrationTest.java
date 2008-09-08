package metagenerics.integration;

import java.io.IOException;

import org.junit.Test;

public class IntegrationTest {
	
	MetaProgramUnderTest metaProgramUnderTest = new MetaProgramUnderTest();
	
	void runIntegrationTest(String folderName) throws IOException {
		metaProgramUnderTest.runIntegrationTest(folderName);
	}

	@Test
	public void accessors() throws IOException {
		runIntegrationTest("accessors");
	}

	@Test
	public void accessorsMultipleFiles() throws IOException {
		runIntegrationTest("accessors_multiple_files");
	}

	@Test
	public void accessorsJar() throws IOException {	
		metaProgramUnderTest.runJarMaking("accessors_jar");
		runIntegrationTest("accessors_jar");
	}

	
	@Test
	public void listeners() throws IOException {
		runIntegrationTest("listener");
	}

	@Test
	public void observer() throws IOException {
		runIntegrationTest("observer");
	}
}
