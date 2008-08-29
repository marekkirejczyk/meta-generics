package metagenerics.symbol.type;

import java.util.ArrayList;
import java.util.List;

import metagenerics.ast.Node;

public class MetaTypeDefSymbol extends Type {

	List<ClassSymbol> arguments = new ArrayList<ClassSymbol>();
	
	MetaGenericSymbol function;
	
	public MetaTypeDefSymbol(Node astNode) {
		setName(astNode.getName());
		setAstNode(astNode);
	}

	public List<ClassSymbol> getArguments() {
		return arguments;
	}

	public void setArguments(List<ClassSymbol> arguments) {
		this.arguments = arguments;
	}

	public MetaGenericSymbol getFunction() {
		return function;
	}

	public void setFunction(MetaGenericSymbol function) {
		this.function = function;
	}
	
	

}
