


meta class PropertyProducer<Bean> {
	private java.beans.PropertyChangeSupport propertyChangeSupport = new java.beans.PropertyChangeSupport(
			this);

	public void addPropertyChangeListener(java.beans.PropertyChangeListener arg0) {
		propertyChangeSupport.addPropertyChangeListener(arg0);
	}

	public void addPropertyChangeListener(String arg0, java.beans.PropertyChangeListener arg1) {
		propertyChangeSupport.addPropertyChangeListener(arg0, arg1);
	}

	public void removePropertyChangeListener(java.beans.PropertyChangeListener arg0) {
		propertyChangeSupport.removePropertyChangeListener(arg0);
	}

	public void removePropertyChangeListener(String arg0, java.beans.PropertyChangeListener arg1) {
		propertyChangeSupport.removePropertyChangeListener(arg0, arg1);
	}
	

	meta {
	    for (metagenerics.ast.Node e: Bean.getChildren())
	      evaluate(e);

	    for (metagenerics.ast.member.Field m: Bean.getFields()) {
	      if (m.hasAnnotation("Property"))
			evaluate("public %1$s get%2$s() {return %3$s;}", m.getType(), util.StringUtils.capitalize(m.getName()), m.getName());
			evaluate("public void set%3$s(%1$s %2$s) {" +
						"%1$s oldValue = this.%2$s;" +
						"this.%2$s = %2$s;" +
						"propertyChangeSupport.firePropertyChange(\"%2$s\", oldValue, %2$s);}",
						m.getType(), m.getName(), util.StringUtils.capitalize(m.getName()));
		}
	}
	
	
	}

}