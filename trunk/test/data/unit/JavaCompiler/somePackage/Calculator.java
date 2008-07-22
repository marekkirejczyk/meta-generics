package somePackage;

import metagenerics.ast.declarations.ClassDeclaration;
import metagenerics.ast.metageneric.Typedef;

public class Calculator extends metagenerics.MetaGeneric {
	
	protected void translateMetaGenerics(Typedef typedef,
			StringBuilder result) {
	
	}

	public void setArgument(int i, ClassDeclaration arg) {
		
	}
	
	public int get() {
		return 13;
	}
	
	public int add(int a, int b) {
		return a + b;
	}
	
}