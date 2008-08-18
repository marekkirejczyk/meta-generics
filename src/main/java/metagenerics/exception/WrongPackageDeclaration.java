package metagenerics.exception;

import java.util.List;

import util.CollectionUtils;

public class WrongPackageDeclaration extends CompileException {

	public WrongPackageDeclaration(List<String> packagePath,
			List<String> filePath) {
		super("Wrong package declaration, declarations is '"
				+ CollectionUtils.toString(packagePath, ".")
				+ "', but file path is like '"
				+ CollectionUtils.toString(filePath, ".") + "'.");
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}

}
