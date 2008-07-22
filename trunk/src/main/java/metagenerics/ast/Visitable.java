package metagenerics.ast;

public interface Visitable {
	public void accept(Visitor visitor);
}
