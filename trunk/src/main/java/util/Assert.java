package util;

@SuppressWarnings("serial")
class CheckFailed extends RuntimeException {

}

public class Assert {
	static public void assertTrue(boolean cond) {
		if (!cond)
			throw new CheckFailed();
	}
}
