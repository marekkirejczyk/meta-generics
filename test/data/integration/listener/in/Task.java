import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


public class TaskStub {

	@Property
	String description;
	
	@Property
	Date date;
	
}

class Task = PropertyProducer<TaskStub>