package metagenerics.transform.parse;

import java.io.File;
import java.io.IOException;

import metagenerics.ast.unit.Unit;
import metagenerics.compile.parse.JavaLexer;
import metagenerics.compile.parse.JavaParser;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

public class MetaJavaParser {

	public Unit parse(String inputFileName) throws IOException, RecognitionException {
		return parse(new File(inputFileName));
	}

	public Unit parse(File inputFile) throws IOException, RecognitionException {
		String path = inputFile.getPath();
		JavaLexer lexer = new JavaLexer();
		lexer.setCharStream(new ANTLRFileStream(path));
		CommonTokenStream tokens = new CommonTokenStream();
		tokens.setTokenSource(lexer);
		JavaParser parser = new JavaParser(tokens);
		return parser.compilationUnit().unit;
	}
}
