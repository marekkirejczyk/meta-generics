package metagenerics.symbol.type;



public class PrimitiveType extends Type {
	
	enum Type {
		INT, LONG, SHORT, FLOAT, DOUBLE, CHAR, BYTE, BOOLEAN
	}
	
	Type type;
	
	private PrimitiveType() {
	}

	
}
