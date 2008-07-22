package metagenerics.ast.common;

import org.antlr.runtime.Token;

import metagenerics.ast.Node;

public class InstructionBlock extends Node {
	
	public InstructionBlock(Token start, Token stop, String text) {
		setInfo(start, stop, text);
	}

	
	
}
