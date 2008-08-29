package metagenerics.ast;

import metagenerics.exception.NotImplementedException;
import metagenerics.symbol.Symbol;

import org.antlr.runtime.Token;

public class Node implements Visitable {

	protected Token start, stop;

	private String text;

	private Symbol symbol;

	public Node() {
	}

	public Node(Node copy) {
		start = copy.start;
		stop = copy.stop;
		symbol = copy.symbol;
		text = copy.text;
	}

	public Node(String text) {
		this.text = text;
	}

	public void setInfo(Token start, Token stop, String text) {
		this.start = start;
		this.stop = stop;
		this.text = text;
	}

	public String getText() {
		return text == null ? "" : text;
	}

	public int getCharPositionInLine() {
		return start.getCharPositionInLine();
	}

	public int getLine() {
		return start.getLine();
	}

	public String getName() {
		throw new NotImplementedException();
	}

	public void accept(Visitor visitor) {
		System.err.println(this.getClass().getName());
		System.out.println(getText());
		throw new NotImplementedException();
	}

	public Symbol getSymbol() {
		return symbol;
	}

	public void setSymbol(Symbol symbol) {
		this.symbol = symbol;
	}

}
