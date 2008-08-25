package metagenerics.ast.member;

import java.util.List;

import metagenerics.ast.common.Argument;

abstract public class AbstractMethod extends Member {

	protected AbstractMethod(AbstractMethod copy) {
		super(copy);
		setArguments(copy.getArguments());
		setGenericParameters(copy.getGenericParameters());
		setExceptions(copy.getExceptions());
		setBlock(copy.getBlock());
		setRest(copy.getRest());
	}
	
	public AbstractMethod() {
		
	}

	List<Argument> arguments;

	List<String> genericParameters;

	List<String> exceptions;

	Block block;

	String rest;

	public List<Argument> getArguments() {
		return arguments;
	}

	public void setArguments(List<Argument> arguments) {
		this.arguments = arguments;
	}

	public List<String> getGenericParameters() {
		return genericParameters;
	}

	public void setGenericParameters(List<String> genericParameters) {
		this.genericParameters = genericParameters;
	}

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	public List<String> getExceptions() {
		return exceptions;
	}

	public void setExceptions(List<String> exceptions) {
		this.exceptions = exceptions;
	}

	public String getRest() {
		return rest;
	}

	public void setRest(String rest) {
		this.rest = rest;
	}

}
