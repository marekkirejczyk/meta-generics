package metagenerics.ast.member;

public class InitialisationBlock extends Member {
	public enum Type {
		INSTANCE, STATIC, META
	}

	Type type = Type.INSTANCE;

	Block block;

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

}
