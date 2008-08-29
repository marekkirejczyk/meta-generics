package framework;

import static util.StringUtils.capitalize;
import metagenerics.ast.*;
import metagenerics.ast.declarations.*;
import metagenerics.ast.member.*;
import metagenerics.ast.common.*;
import metagenerics.runtime.*;

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
	void evaluateGetter(Field m) {
		String property = m.getName();
		String name = "get" + capitalize(property);
		String type = m.getType();
		evaluate("public %1$s %2$s() {return %3$s;}", type, name, property);
	}

	@Meta
	void evaluateSetter(Field m) {
		String property = m.getName();
		String type = m.getType();
		String name = "set" + capitalize(m.getName());
		evaluate("public void %1$s(%2$s arg) {%3$s = arg;}", name, type,
				property);
	}

	protected void translateMetaGenerics(
			metagenerics.ast.metageneric.MetaTypedefAst typedef,
			StringBuilder result) {
		{
			evaluateAll(C.getChildren());

			for (Field m : C.getFields())
				if (m.hasAnnotation("Getter") || m.hasAnnotation("Accessors"))
					evaluateGetter(m);

			for (Field m : C.getFields())
				if (m.hasAnnotation("Setter") || m.hasAnnotation("Accessors"))
					evaluateSetter(m);
		}
	}
}