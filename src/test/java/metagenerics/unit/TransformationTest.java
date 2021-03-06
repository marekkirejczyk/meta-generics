package metagenerics.unit;

import static metagenerics.TestHelper.assertEqualsFileAndStringTokens;
import static metagenerics.TestHelper.getUnitTestFileName;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

import metagenerics.TestHelper;
import metagenerics.ast.declarations.ClassDeclaration;
import metagenerics.ast.metageneric.MetaGenericAst;
import metagenerics.ast.metageneric.MetaTypedefAst;
import metagenerics.ast.unit.UnitAst;
import metagenerics.pipe.common.CustomDirClassLoader;
import metagenerics.pipe.phase1.parse.MetaJavaParser;
import metagenerics.pipe.phase2.MetaGenericProxyLoader;
import metagenerics.pipe.phase2.MetaGenericTransform;
import metagenerics.pipe.phase3.TypedefTransform;
import metagenerics.runtime.MetaGeneric;
import metagenerics.symbol.PackageSymbol;
import metagenerics.symbol.type.ClassSymbol;
import metagenerics.symbol.type.MetaGenericSymbol;
import metagenerics.symbol.type.MetaTypeDefSymbol;

import org.antlr.runtime.RecognitionException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import util.FileUtils;

public class TransformationTest {

	static final String GENERIC_IN_FILE_NAME = getUnitTestFileName("MetaGenericTransform/in/metageneric.in");

	static final String STUB_IN_FILE_NAME = getUnitTestFileName("MetaGenericTransform/in/stub.in");

	static final String META_TYPEDEF_IN_FILE_NAME = getUnitTestFileName("MetaGenericTransform/in/typedef.in");

	static final String GENERIC_OUT_FILE_NAME = getUnitTestFileName("MetaGenericTransform/out/metageneric.out");

	static final String TYPEDEF_OUT_FILE_NAME = getUnitTestFileName("MetaGenericTransform/out/typedef.out");

	static final String GENERIC_INTERMEDIATE_FILE_NAME = getUnitTestFileName("MetaGenericTransform/int/Accessors.java");

	static final String INTERMEDIATE_CLASS_NAME = "Accessors";

	static final String INTERMEDIATE_FOLDER = getUnitTestFileName("MetaGenericTransform/int");

	@Before
	public void before() {
		File intDir = new File(INTERMEDIATE_FOLDER);
		if (!intDir.isDirectory())
			intDir.mkdir();
	}

	@Test
	public void metagenericTransformTest() throws IOException,
			RecognitionException {
		StringBuilder result = new StringBuilder();
		MetaJavaParser parser = new MetaJavaParser();
		UnitAst unitAST = parser.parse(GENERIC_IN_FILE_NAME);
		MetaGenericAst metaGeneric = (MetaGenericAst) unitAST.getElements()
				.getElements().get(0);
		MetaGenericTransform t = new MetaGenericTransform();
		t.transform(metaGeneric, result);

		assertEqualsFileAndStringTokens(GENERIC_OUT_FILE_NAME, result
				.toString());
	}

	public metagenerics.runtime.MetaGeneric firstMetaGenericInFile(
			String filename) throws IOException, ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			RecognitionException {

		MetaGenericAst metaGenericAst = TestHelper.firstASTInFile(
				MetaGenericAst.class, GENERIC_IN_FILE_NAME);

		MetaGenericProxyLoader compiler = new MetaGenericProxyLoader();
		compiler.setIntermediateFolder(INTERMEDIATE_FOLDER);

		return compileMetaGeneric(metaGenericAst);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void metatypedefManualTransformTest() throws IOException,
			RecognitionException, ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			IllegalArgumentException, SecurityException, NoSuchFieldException {

		StringBuilder result = new StringBuilder();

		ClassDeclaration stub = TestHelper.firstASTInFile(
				ClassDeclaration.class, STUB_IN_FILE_NAME);
		MetaTypedefAst typedef = TestHelper.firstASTInFile(
				MetaTypedefAst.class, META_TYPEDEF_IN_FILE_NAME);
		MetaGenericAst metaGenericAst = TestHelper.firstASTInFile(
				MetaGenericAst.class, GENERIC_IN_FILE_NAME);

		MetaGenericSymbol symbol = new MetaGenericSymbol(metaGenericAst);
		symbol.setParent(new PackageSymbol(""));
		metagenerics.runtime.MetaGeneric generic = firstMetaGenericInFile(GENERIC_INTERMEDIATE_FILE_NAME);
		generic.setArgument(1, stub);
		generic.generateClass(typedef, result);

		assertEqualsFileAndStringTokens(TYPEDEF_OUT_FILE_NAME, result
				.toString());
	}

	@Test
	public void metatypedefTransformTest() throws IOException,
			RecognitionException, ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		StringBuilder result = new StringBuilder();
		TypedefTransform transform = new TypedefTransform();
		PackageSymbol rootPackage = new PackageSymbol("");

		MetaTypedefAst typedef = TestHelper.firstASTInFile(
				MetaTypedefAst.class, META_TYPEDEF_IN_FILE_NAME);
		metagenerics.runtime.MetaGeneric generic = firstMetaGenericInFile(GENERIC_IN_FILE_NAME);
		MetaGenericAst genericAst = TestHelper.firstASTInFile(
				MetaGenericAst.class, GENERIC_IN_FILE_NAME);
		ClassDeclaration stub = TestHelper.firstASTInFile(
				ClassDeclaration.class, STUB_IN_FILE_NAME);

		genericAst.setMetagenericInstance(generic);
		MetaTypeDefSymbol metaTypeDefSymbol = new MetaTypeDefSymbol(typedef);
		MetaGenericSymbol metaGenericSymbol = new MetaGenericSymbol(genericAst);
		metaTypeDefSymbol.setParent(rootPackage);
		metaGenericSymbol.setParent(rootPackage);
		rootPackage.add(new ClassSymbol(stub));
		rootPackage.add(metaGenericSymbol);
		metaTypeDefSymbol.setFunction(metaGenericSymbol);
		typedef.setSymbol(metaTypeDefSymbol);
		metaTypeDefSymbol.getArguments().add(new ClassSymbol(stub));
		transform.transform(typedef, result);

		assertEqualsFileAndStringTokens(TYPEDEF_OUT_FILE_NAME, result
				.toString());
	}

	@Test
	public void metaCompilator() throws IOException, ClassNotFoundException,
			RecognitionException, InstantiationException,
			IllegalAccessException {

		MetaGenericAst metaGeneric = TestHelper.firstASTInFile(
				MetaGenericAst.class, GENERIC_IN_FILE_NAME);
		MetaGeneric genericClass = compileMetaGeneric(metaGeneric);
		Assert.assertNotNull(genericClass);
	}

	protected MetaGeneric compileMetaGeneric(MetaGenericAst ast)
			throws IOException, InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		StringBuilder result = new StringBuilder();
		MetaGenericTransform mgt = new MetaGenericTransform();
		mgt.transform(ast, result);

		FileUtils.save(GENERIC_INTERMEDIATE_FILE_NAME, result.toString());
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(
				null, null, null);

		Iterable<? extends JavaFileObject> compilationUnits = fileManager
				.getJavaFileObjectsFromFiles(Collections.singleton(new File(
						GENERIC_INTERMEDIATE_FILE_NAME)));

		CompilationTask task = compiler.getTask(null, fileManager, null, Arrays
				.asList(new String[] { "-d", INTERMEDIATE_FOLDER }), null,
				compilationUnits);
		task.call();
		CustomDirClassLoader loader = new CustomDirClassLoader(
				INTERMEDIATE_FOLDER);
		Class<? extends Object> genericClass = loader
				.loadClass(INTERMEDIATE_CLASS_NAME);
		return (MetaGeneric) genericClass.newInstance();
	}
}
