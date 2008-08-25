package metagenerics;

import java.io.IOException;

import metagenerics.integration.MetaProgramUnderTest;

public class GuiTest {
	MetaProgramUnderTest metaProgramUnderTest = new MetaProgramUnderTest();

	GuiTest() throws IOException {
		metaProgramUnderTest.runGuiTest("observer");
	}
	
	static public void main(String [] args) throws IOException {
		new GuiTest();
	}
	

}
