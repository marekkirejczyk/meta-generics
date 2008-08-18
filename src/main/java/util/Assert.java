package util;

@SuppressWarnings("serial")
class AssertionFailed extends RuntimeException {

}

public class Assert {
	static public void assertTrue(boolean cond) {
		if (!cond)
			throw new AssertionFailed();
	}
}
