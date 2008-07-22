package metagenerics.ast.unit;

import metagenerics.ast.Node;

public class Import extends Node {

	boolean isStatic;

	String path;

	public Import(String packagePath) {
		this.path = packagePath;
	}

	public String getPackagePath() {
		return path;
	}

	public boolean isStatic() {
		return isStatic;
	}

	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}

}
