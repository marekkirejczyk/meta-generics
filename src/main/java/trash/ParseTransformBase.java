package trash;

import java.io.File;
import java.io.IOException;

import metagenerics.ast.unit.Unit;
import metagenerics.transform.parse.MetaJavaParser;

import org.antlr.runtime.RecognitionException;


abstract public class ParseTransformBase extends FileTransform {

	public abstract void compile(Unit unit, File outputFileName);

	@Override
	public void compile(File inputFileName, File outputFileName) {
		try {
			compile(new MetaJavaParser().parse(inputFileName),
					outputFileName);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RecognitionException e) {
			e.printStackTrace();
		}
	}

}
