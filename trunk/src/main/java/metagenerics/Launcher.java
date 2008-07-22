package metagenerics;

public class Launcher {

	void printInfo() {
		String msg = "\tmetagenerics -src <dir:dir:...> -intermediate <dir> -target <dir>";
		System.out.println("Usage:");
		System.out.println(msg);
	}

	void run() {
		printInfo();
	}

	static public void main(String[] args) {
		new Launcher().run();
	}
}
