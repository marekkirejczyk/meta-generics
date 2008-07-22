class Accessors extends MetaGeneric {
	public metagenerics.ast.declarations.ClassDeclaration C;

	protected void translateMetaGenerics(Typedef typedef, StringBuilder result) 
{
    for (Element e: C.getElements())
      evaluate(e);

    for (Method m: C.getMethods())
      if (m.hasAnnotation("Getter") || m.hasAnnotation("Accessors"))
    	evaluate("{1} get{2}() {return {3};}", e.getType(), e.getName().capitalize(), e.getName());

    for (Method m: C.getMethods())
      if (m.hasAnnotation("Setter") || m.hasAnnotation("Accessors"))
    	evaluate("void set{2}({2} arg) {{3} = arg;}", e.getType(), e.getName().capitalize(), e.getName());

  }
