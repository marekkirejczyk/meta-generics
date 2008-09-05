package framework;

import static util.StringUtils.capitalize;
import metagenerics.ast.*;
import metagenerics.ast.declarations.*;
import metagenerics.ast.member.*;
import metagenerics.ast.common.*;
import metagenerics.runtime.*;

@Meta
class AccessorsUtils {
	static void evaluateGetter(MetaGeneric t, Field m) {
		String property = m.getName();
		String name = "get" + capitalize(property);
		String type = m.getType();
		t
				.evaluate(
						"\n		     public %1$s %2$s() { \n		    	 return %3$s;\n		     }",
						type, name, property);
	}

}

public class AddAccessors extends metagenerics.runtime.MetaGeneric {
	public metagenerics.ast.declarations.ClassDeclaration C;

	public void setArgument(int i,
			metagenerics.ast.declarations.ClassDeclaration arg) {
		switch (i) {
		case 1:
			this.C = arg;
			break;
		default:
			throw new metagenerics.exception.UnexpectedArgumentIndexException(i);
		}
	}

	@Meta
	void evaluateSetter(Field m) {
		String property = m.getName();
		String type = m.getType();
		String name = "set" + capitalize(m.getName());
		evaluate(
				"\n		 		public void %1$s(%2$s arg) {\n		 			%3$s = arg;\n		 		}",
				name, type, property);
	}

	protected void translateMetaGenerics(
			metagenerics.ast.metageneric.MetaTypedefAst typedef,
			StringBuilder result) {
		{
			evaluateAll(C.getChildren());

			for (Field m : C.getFields())
				if (m.hasAnnotation("Getter") || m.hasAnnotation("Accessors"))
					AccessorsUtils.evaluateGetter(this, m);

			for (Field m : C.getFields())
				if (m.hasAnnotation("Setter") || m.hasAnnotation("Accessors"))
					evaluateSetter(m);
		}
	}
}