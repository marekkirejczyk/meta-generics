package metagenerics.integration;


import static metagenerics.TestHelper.getIntegrationTestFileName;

import java.awt.SystemTray;
import java.io.File;
import java.io.IOException;
import java.util.Collections;


import metagenerics.walkers.MetaCompiler;

import org.junit.Test;
import org.junit.Ignore;

import com.sun.corba.se.spi.ior.iiop.JavaCodebaseComponent;

import sun.reflect.Reflection;
import sun.reflect.ReflectionFactory;
import trash.MetaGenericPreCompiler;
import util.FolderUtils;

public class MetaCompilerTest {

	static final String SOURCE_FOLDER = getIntegrationTestFileName("accessors/in/");

	static final String INTERMEDIATE_FOLDER = getIntegrationTestFileName("accessors/int/");

	static final String OUTPUT_FOLDER = getIntegrationTestFileName("accessors/out/");

	static final String TARGET_FOLDER = getIntegrationTestFileName("accessors/bin/");

	
	
	@Test
	public void simpleTest() throws IOException {
		MetaCompiler compiler = new MetaCompiler();
		compiler.setSourceFolders(Collections.singletonList(SOURCE_FOLDER));
		compiler.setIntermediateFolder(INTERMEDIATE_FOLDER);
		compiler.setTargetFolder(TARGET_FOLDER);
		compiler.compile();
	}
	
}
