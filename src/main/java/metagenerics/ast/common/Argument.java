package metagenerics.ast.common;

public class Argument {
	String type;

	String name;

	public Argument(String type, String name) {
		this.type = type;
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

}
