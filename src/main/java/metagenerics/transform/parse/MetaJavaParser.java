package metagenerics.transform.parse;

import java.io.File;
import java.io.IOException;

import metagenerics.ast.unit.UnitAst;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

public class MetaJavaParser {

	public UnitAst parse(String inputFileName) throws IOException,
			RecognitionException {
		return parse(new File(inputFileName));
	}

	public UnitAst parse(File inputFile) throws IOException,
			RecognitionException {
		String path = inputFile.getPath();
		metagenerics.compile.parse.MetaJavaLexer lexer = new metagenerics.compile.parse.MetaJavaLexer();
		lexer.setCharStream(new ANTLRFileStream(path));
		CommonTokenStream tokens = new CommonTokenStream();
		tokens.setTokenSource(lexer);
		metagenerics.compile.parse.MetaJavaParser parser = new metagenerics.compile.parse.MetaJavaParser(
				tokens);
		return parser.compilationUnit().unit;
	}
}
