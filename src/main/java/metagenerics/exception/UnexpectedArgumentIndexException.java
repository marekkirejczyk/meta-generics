package metagenerics.exception;

@SuppressWarnings("serial")
public class UnexpectedArgumentIndexException extends RuntimeException {

	public UnexpectedArgumentIndexException(int index) {
		super("Unexpected argument with index: " + index);

	}

}
