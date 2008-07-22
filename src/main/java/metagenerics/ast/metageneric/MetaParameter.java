package metagenerics.ast.metageneric;

public class MetaParameter {
	public enum Type {
		Class, Interface, String, Enum 
	}
	
	String name;

	public MetaParameter(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
