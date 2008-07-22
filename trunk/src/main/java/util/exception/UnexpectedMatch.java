package util.exception;

public class UnexpectedMatch extends RuntimeException {
	Class klass;

	public UnexpectedMatch(Class klass) {
		super("Unexpeted match in switch or ifelse construction: " + klass.getCanonicalName());
		this.klass = klass;
	}

	
	
}
