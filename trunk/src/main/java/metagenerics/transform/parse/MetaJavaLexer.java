package metagenerics.transform.parse;

import java.io.IOException;
import java.util.List;

import metagenerics.compile.parse.JavaLexer;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.CommonTokenStream;

public class MetaJavaLexer {

	static public List<CommonToken> tokenizeStream(CharStream cs)
			throws IOException {
		JavaLexer lexer = new JavaLexer();
		lexer.setCharStream(cs);
		CommonTokenStream tokens = new CommonTokenStream();
		tokens.setTokenSource(lexer);
		return tokens.getTokens();
	}

	static public List<CommonToken> tokenizeString(String text)
			throws IOException {
		return tokenizeStream(new ANTLRStringStream(text));
	}

	static public List<CommonToken> tokenizeFile(String path)
			throws IOException {
		return tokenizeStream(new ANTLRFileStream(path));
	}

}
