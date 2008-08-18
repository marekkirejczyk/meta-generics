public class Accessors extends metagenerics.runtime.MetaGeneric {
	public metagenerics.ast.declarations.ClassDeclaration C;

	public void setArgument(int i, metagenerics.ast.declarations.ClassDeclaration arg) {
		switch (i) {
			case 1: this.C = arg;break;
			default: throw new metagenerics.exception.UnexpectedArgumentIndexException(i);
		}
	}

	protected void translateMetaGenerics(metagenerics.ast.metageneric.MetaTypedefAst typedef, StringBuilder result) { 
{
    for (metagenerics.ast.Node e: C.getChildren())
      evaluate(e);

    for (metagenerics.ast.member.Field m: C.getFields())
      if (m.hasAnnotation("Getter") || m.hasAnnotation("Accessors"))
    	evaluate("%1$s get%2$s() {return %3$s;}", m.getType(), util.StringUtils.capitalize(m.getName()), m.getName());

    for (metagenerics.ast.member.Field m: C.getFields())
      if (m.hasAnnotation("Setter") || m.hasAnnotation("Accessors"))
    	evaluate("void set%2$s(%1$s arg) {%3$s = arg;}", m.getType(), util.StringUtils.capitalize(m.getName()), m.getName());
  }
 } }
