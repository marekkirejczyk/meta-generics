package metagenerics.ast.member;

import java.util.List;

import metagenerics.ast.common.Argument;

public class AbstractMethod  extends Member {
	List<Argument> arguments;

	List<Argument> genericParameters;

	Block block;

	public List<Argument> getArguments() {
		return arguments;
	}

	public void setArguments(List<Argument> arguments) {
		this.arguments = arguments;
	}

	public List<Argument> getGenericParameters() {
		return genericParameters;
	}

	public void setGenericParameters(List<Argument> genericParameters) {
		this.genericParameters = genericParameters;
	}

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

}
