package metagenerics.ast;

import metagenerics.symbol.Symbol;
import metagenerics.symbol.SymbolTable;

import org.antlr.runtime.Token;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Node implements Symbol, Visitable {

	protected Token start, stop;

	private String text;

	private SymbolTable symbolTable;
	
	public Node() {
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

	public SymbolTable getSymbolTable() {
		return symbolTable;
	}

	public void setSymbolTable(SymbolTable symbolTable) {
		this.symbolTable = symbolTable;
	}

	
}
