package metagenerics.ast.common;

public enum Modifier {
	PUBLIC, 
	PROTECTED, 
	PRIVATE,
	STRICT,
	STATIC,
	TRANSIENT,
	ABSTRACT,
    FINAL,
    INTERFACE,
    NATIVE,
    SYNCHRONIZED,
    VOLATILE;

    public static Modifier fromText(String value) {
		return Modifier.valueOf(value.toUpperCase());
	}
}
