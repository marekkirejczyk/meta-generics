package metagenerics.ast.metageneric;

import java.util.List;

import metagenerics.ast.Visitor;

public class Typedef extends MetaDeclaration {

	String textAfterTransformation;

	String function;

	List<String> parameters;

	public List<String> getParameters() {
		return parameters;
	}

	public void setParameters(List<String> parameters) {
		this.parameters = parameters;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String getText() {
		return (textAfterTransformation == null) ? super.getText()
				: textAfterTransformation;
	}

	public void setTextAfterTransformation(String textAfterTransformation) {
		this.textAfterTransformation = textAfterTransformation;
	}

	public String getTextAfterTransformation() {
		return textAfterTransformation;
	}

}
