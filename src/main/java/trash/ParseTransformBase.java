package trash;

import java.io.File;
import java.io.IOException;

import metagenerics.ast.unit.UnitAst;
import metagenerics.io.FileTransform;
import metagenerics.transform.parse.MetaJavaParser;

import org.antlr.runtime.RecognitionException;

public abstract class ParseTransformBase extends FileTransform {

	public abstract void compile(UnitAst unit, File outputFileName);

	@Override
	public void compile(File inputFileName, File outputFileName) {
		try {
			compile(new MetaJavaParser().parse(inputFileName), outputFileName);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RecognitionException e) {
			e.printStackTrace();
		}
	}

}
