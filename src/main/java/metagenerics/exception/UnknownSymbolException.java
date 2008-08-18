package metagenerics.exception;

public class UnknownSymbolException extends CompileException {
	
	public UnknownSymbolException(String symbol) {
		super("Unknown symbol: " + symbol);
	}

}
