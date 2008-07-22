package metagenerics.ast;

public class CodePosition {

	protected int line;

	protected int position;

	protected String text;

	public CodePosition(int line, int position, String text) {
		this.line = line;
		this.position = position;
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public int getLine() {
		return line;
	}

	public int getPosition() {
		return position;
	}

	public void setText(String text2) {
		// TODO Auto-generated method stub
		
	}

}