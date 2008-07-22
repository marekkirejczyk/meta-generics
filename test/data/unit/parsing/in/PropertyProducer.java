
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

meta class PropertyProducer<Bean> {
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);

	public void addPropertyChangeListener(PropertyChangeListener arg0) {
		propertyChangeSupport.addPropertyChangeListener(arg0);
	}

	public void addPropertyChangeListener(String arg0, PropertyChangeListener arg1) {
		propertyChangeSupport.addPropertyChangeListener(arg0, arg1);
	}

	public void removePropertyChangeListener(PropertyChangeListener arg0) {
		propertyChangeSupport.removePropertyChangeListener(arg0);
	}

	public void removePropertyChangeListener(String arg0, PropertyChangeListener arg1) {
		propertyChangeSupport.removePropertyChangeListener(arg0, arg1);
	}
	

	meta {
	    for (metagenerics.ast.Node e: Bean.getChildren())
	      evaluate(e);

	    for (metagenerics.ast.member.Field m: Bean.getFields()) {
	      if (m.hasAnnotation("Property"))
	    	evaluate("%1$s get%2$s() {return %3$s;}", m.getType(), util.StringUtils.capitalize(m.getName()), m.getName());
			evaluate("public %1$s get%2$s() {return %3$s;}", m.getType(), util.StringUtils.capitalize(m.getName()), m.getName());
			evaluate("public void setDate(%1$s %2$s) {" +
						"%1$s oldValue = this.%2$s;" +
						"this.%2$s = %2$s;" +
						"propertyChangeSupport.firePropertyChange(\"%2$s\", oldValue, %2$s);",
						m.getType(), m.getName());
		}
	}
	
	
	}

}