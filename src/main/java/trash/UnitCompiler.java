package trash;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import metagenerics.ast.unit.Unit;
import metagenerics.compile.parse.JavaLexer;
import metagenerics.compile.parse.JavaParser;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

public class UnitCompiler {

	String outputDirectory;
	
	public String getOutputDirectory() {
		return outputDirectory;
	}

	public void setOutputDirectory(String outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

	public Unit parseFile(File file) throws IOException, RecognitionException {
		String path = file.getAbsolutePath();
		JavaLexer lexer = new JavaLexer();
		lexer.setCharStream(new ANTLRFileStream(path));
		CommonTokenStream tokens = new CommonTokenStream();
		tokens.setTokenSource(lexer);
		JavaParser parser = new JavaParser(tokens);
		Unit unit = parser.compilationUnit().unit;
		return unit;
	}

	protected void phase1(File file, String outDir, Unit unit) throws IOException, RecognitionException {
		File dir = new File(outDir);
		
		if (!dir.isDirectory())
			dir.mkdirs();
		String fileName = outDir +  File.separatorChar + file.getName();
		FileOutputStream fos = new FileOutputStream(fileName);
		PrintStream ps = new PrintStream(fos);
		ps.print(unit);
		/*if (unit.hasMetaDeclarations()) {
			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			compiler.run(null, null, null, fileName);
		}*/

		fos.close();
	}

	protected void generateTypeDef(File file, String outDir, Unit unit) throws IOException, RecognitionException {
		FileOutputStream fos = new FileOutputStream(outDir
				+ File.separatorChar + file.getName());
		PrintStream ps = new PrintStream(fos);
		ps.print(unit);
		fos.close();
	}

	
	protected void phase2(File file, String outDir, Unit unit) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		/*for (Element type: unit.getTypes())
			if (type instanceof MetaGeneric)
				new MetaGenericComilation(file, outDir, outputDirectory, unit, (MetaGeneric)type).generate();
				*/
	}

	protected void phase3(File file, String outDir, Unit unit) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		/*for (Element type: unit.getTypes())
			if (type instanceof MetaTypeDef)
				new TypedefCompilation(file, outDir, outputDirectory, unit, (MetaTypeDef)type).generate();
				*/
	}
	
	
	protected void doCompile(File file, String outDir) throws IOException, RecognitionException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		Unit unit = parseFile(file);
		phase1(file, outDir, unit);
		phase2(file, outDir, unit);
		phase3(file, outDir, unit);
	}
	
	public void compile(File file, String outDir) {
		try {
			doCompile(file, outDir);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RecognitionException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
