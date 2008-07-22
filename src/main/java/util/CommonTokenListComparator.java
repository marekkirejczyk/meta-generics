package util;

import static util.StringUtils.isWhite;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.antlr.runtime.CommonToken;

public class CommonTokenListComparator implements Comparator<List<CommonToken>> {

	String error = "";

	String astTrace = "";

	String fileTrace = "";

	String astRest = "";

	String fileRest = "";

	private String rest(Iterator<CommonToken> i) {
		String result = "";
		while (i.hasNext())
			result += i.next().getText();
		return result;
	}

	private boolean compareToken(CommonToken c, CommonToken d,
			Iterator<CommonToken> astIterator,
			Iterator<CommonToken> fileIterator) {
		astTrace += c.getText() + " ";
		fileTrace += d.getText() + " ";
		if (c.getText().equals(d.getText()))
			return true;

		error = "Expected \"" + c.getText() + "\"" + " at line " + c.getLine()
				+ " (" + c.getCharPositionInLine() + "), " + "but found: \""
				+ d.getText() + "\"" + " at line " + d.getLine() + " ("
				+ c.getCharPositionInLine() + ")";

		astTrace += rest(astIterator);
		fileTrace += rest(fileIterator);

		return false;
	}

	public int compare(List<CommonToken> expectedTokens, List<CommonToken> wasTokens) {
		astTrace = "";
		fileTrace = "";
		Iterator<CommonToken> astIterator = expectedTokens.iterator();
		Iterator<CommonToken> fileIterator = wasTokens.iterator();

		while (astIterator.hasNext() && fileIterator.hasNext()) {
			CommonToken t = astIterator.next();
			CommonToken u = fileIterator.next();
			while (isWhite(t) && astIterator.hasNext())
				t = astIterator.next();
			while (isWhite(u) && fileIterator.hasNext())
				u = fileIterator.next();
			
			if (!isWhite(t) || !isWhite(u)) 
				if (!compareToken(t, u, astIterator, fileIterator))
					return -1;
		}
		
		if (astIterator.hasNext() || fileIterator.hasNext()) {
			astRest = rest(astIterator);
			fileRest = rest(fileIterator);
			if (!isWhite(astRest) || !isWhite(fileRest)) {
				if (!astRest.equals("")) {
					error = "Expected \"" + astRest + "\", but nothing found.";
					return 1;
				}
				if (!fileRest.equals("")) {
					error = "Unexpected at the end \"" + fileRest + "\"";
					return -1;
				}
			}
		}
		return 0;
	}

	public String getError() {
		return error;
	}

	
	
}
