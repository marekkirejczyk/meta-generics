package metagenerics.ast.member;

import metagenerics.ast.Visitor;
import metagenerics.ast.common.InstructionBlock;

public class Block extends Member {
	public enum Type {
		STATIC, NORMAL, META
	}

	Type type = Type.NORMAL;

	InstructionBlock instructionBlock;
	
	
	public InstructionBlock getInstructionBlock() {
		return instructionBlock;
	}

	public void setInstructionBlock(InstructionBlock instructionBlock) {
		this.instructionBlock = instructionBlock;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
	
	

}