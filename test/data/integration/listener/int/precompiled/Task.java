

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Date;
import metalib.bean.*;
import static util.StringUtils.*;
import metalib.bean.AccessorsUtils;

class TaskStub{
	@Property
	String description;

	@Property
	Date date;

}
public class Task {
private java.beans.PropertyChangeSupport propertyChangeSupport = new java.beans.PropertyChangeSupport(			this);
public void addPropertyChangeListener(java.beans.PropertyChangeListener arg0) {		propertyChangeSupport.addPropertyChangeListener(arg0);	}
public void addPropertyChangeListener(String arg0, java.beans.PropertyChangeListener arg1) {		propertyChangeSupport.addPropertyChangeListener(arg0, arg1);	}
public void removePropertyChangeListener(java.beans.PropertyChangeListener arg0) {		propertyChangeSupport.removePropertyChangeListener(arg0);	}
public void removePropertyChangeListener(String arg0, java.beans.PropertyChangeListener arg1) {		propertyChangeSupport.removePropertyChangeListener(arg0, arg1);	}
@Property
	String description;

@Property
	Date date;


		     public String getDescription() { 
		    	 return description;
		     }

			public void setDescription(String description) {
				String oldValue = this.description;
				this.description = description;
				propertyChangeSupport.firePropertyChange("description", oldValue, description);
			}

		     public Date getDate() { 
		    	 return date;
		     }

			public void setDate(Date date) {
				Date oldValue = this.date;
				this.date = date;
				propertyChangeSupport.firePropertyChange("date", oldValue, date);
			}
}
 