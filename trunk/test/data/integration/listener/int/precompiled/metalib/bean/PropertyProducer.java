package metalib.bean;

import static util.StringUtils.*;
import metalib.bean.AccessorsUtils;
import metagenerics.ast.*;
import metagenerics.ast.declarations.*;
import metagenerics.ast.member.*;
import metagenerics.ast.common.*;
import metagenerics.runtime.*;

 class PropertyProducer extends metagenerics.runtime.MetaGeneric {
	public metagenerics.ast.declarations.ClassDeclaration Bean;

	public void setArgument(int i, metagenerics.ast.declarations.ClassDeclaration arg) {
		switch (i) {
			case 1: this.Bean = arg;break;
			default: throw new metagenerics.exception.UnexpectedArgumentIndexException(i);
		}
	}

	protected void translateMetaGenerics(metagenerics.ast.metageneric.MetaTypedefAst typedef, StringBuilder result) { 
evaluate("private java.beans.PropertyChangeSupport propertyChangeSupport = new java.beans.PropertyChangeSupport(			this);");evaluate("public void addPropertyChangeListener(java.beans.PropertyChangeListener arg0) {		propertyChangeSupport.addPropertyChangeListener(arg0);	}");evaluate("public void addPropertyChangeListener(String arg0, java.beans.PropertyChangeListener arg1) {		propertyChangeSupport.addPropertyChangeListener(arg0, arg1);	}");evaluate("public void removePropertyChangeListener(java.beans.PropertyChangeListener arg0) {		propertyChangeSupport.removePropertyChangeListener(arg0);	}");evaluate("public void removePropertyChangeListener(String arg0, java.beans.PropertyChangeListener arg1) {		propertyChangeSupport.removePropertyChangeListener(arg0, arg1);	}");{
		evaluateAll(Bean.getChildren());
	    for (Field f: Bean.getFields()) {
	      if (f.hasAnnotation("Property")) {
	    	  AccessorsUtils.evaluateGetter(this, f);
	    	  AccessorsUtils.evaluateObservedSetter(this, f);
	      }
		}
	}	}
}
