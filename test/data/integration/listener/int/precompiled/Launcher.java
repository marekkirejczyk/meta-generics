


public class Launcher implements java.beans.PropertyChangeListener{
Launcher() {
		Task task = new Task();
		task.addPropertyChangeListener(this);
		task.setDescription("Ala ma kota");
	}	public void propertyChange(java.beans.PropertyChangeEvent event) {
		System.out.println("Property has changed!");
	}

	public static void main(String [] args) {
		new Launcher();
	}

}
