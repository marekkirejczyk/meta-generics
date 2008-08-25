package metagenerics.transform.metageneric;

import java.io.IOException;

import metagenerics.ast.metageneric.MetaGenericAst;
import metagenerics.ast.unit.ImportAst;
import metagenerics.exception.CompileException;
import metagenerics.runtime.MetaGeneric;
import metagenerics.transform.javacompile.JavaInPlaceCompiler;
import metagenerics.transform.parse.PrettyPrinter;
import util.FileUtils;
import util.PathUtils;

public class MetaGenericCompiler {
	private String intermediateFolder;

	public String getIntermediateFolder() {
		return intermediateFolder;
	}

	public void setIntermediateFolder(String intermediateFolder) {
		this.intermediateFolder = intermediateFolder;
	}

	JavaInPlaceCompiler compiler = new JavaInPlaceCompiler();

	MetaGenericTransform metaGenericTransform = new MetaGenericTransform();

	public MetaGeneric compile(MetaGenericAst ast) {
		try {
			return strict_compile(ast);
		} catch (IOException e) {
			throw new CompileException(e);
		} catch (InstantiationException e) {
			throw new CompileException(e);
		} catch (IllegalAccessException e) {
			throw new CompileException(e);
		} catch (ClassNotFoundException e) {
			throw new CompileException(e);
		}
	}

	public MetaGeneric strict_compile(MetaGenericAst ast) throws IOException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		StringBuilder classText = new StringBuilder();
		String javaName = PathUtils.sourceDirectoryAndClassToJavaFileName(
				intermediateFolder, ast.getName());

		PrettyPrinter printer = new PrettyPrinter(classText);
		for (ImportAst importAst: ast.getImports()) 
			importAst.accept(printer);
		

		
		
		metaGenericTransform.setUseOriginalModifiers(false);
		metaGenericTransform.transform(ast, classText);

		FileUtils.save(javaName, classText.toString());

		Class genericClass = compiler
				.compile(intermediateFolder, ast.getName());

		return (metagenerics.runtime.MetaGeneric) genericClass.newInstance();
	}
}
