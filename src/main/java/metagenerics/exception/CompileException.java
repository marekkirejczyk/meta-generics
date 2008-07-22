package metagenerics.exception;

import metagenerics.ast.CodePosition;

public class CompileException extends RuntimeException {
	CodePosition node;

	public CompileException() {
		super();
	}

	public CompileException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public CompileException(String arg0) {
		super(arg0);
	}

	public CompileException(Throwable arg0) {
		super(arg0);
	}
	
}
