package util.exception;

import java.io.IOException;

public class NotAFileOrFolderException extends IOException {

	public NotAFileOrFolderException(String elementName) {
		super(elementName);
	}

	@Override
	public String getMessage() {
		return "'" + super.getMessage() + "' is not a file or a folder.";
	}

}
