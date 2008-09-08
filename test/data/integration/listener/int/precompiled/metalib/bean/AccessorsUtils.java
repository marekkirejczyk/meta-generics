package metalib.bean;

import static util.StringUtils.capitalize;
import metagenerics.runtime.*;
import metagenerics.ast.member.*;

@Meta
public class AccessorsUtils{
	public static String capitalize(String s) {
		if (s == null || s.length() < 1)
			return s;
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	public static void evaluateGetter(MetaGeneric t, Field m) {
		 String property = m.getName();
		 String name = "get" + capitalize(property);
		 String type = m.getType();
		 t.evaluate("\n		     public %1$s %2$s() { \n		    	 return %3$s;\n		     }",
		 type, name, property);		 
	 }

	public static void evaluateObservedSetter(MetaGeneric t, Field m) {
		String property = m.getName();
		String name = "set" + capitalize(property);
		String type = m.getType();

		t.evaluate("\n			public void %3$s(%1$s %2$s) {\n				%1$s oldValue = this.%2$s;\n				this.%2$s = %2$s;\n				propertyChangeSupport.firePropertyChange(\"%2$s\", oldValue, %2$s);\n			}",
		type, property, name);
	 }

}
