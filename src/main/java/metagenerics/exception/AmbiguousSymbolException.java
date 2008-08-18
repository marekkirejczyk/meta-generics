package metagenerics.exception;

public class AmbiguousSymbolException extends CompileException {
	
	public AmbiguousSymbolException(String symbol) {
		super("Unknown symbol: " + symbol);
	}

}
