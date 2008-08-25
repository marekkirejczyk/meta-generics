package metagenerics.ast.metageneric;

import java.util.ArrayList;
import java.util.List;

import metagenerics.ast.Visitor;
import metagenerics.ast.unit.ImportAst;

public class MetaTypedefAst extends MetaDeclaration {

	String textAfterTransformation;

	String function;

	List<String> parameters;

	List<ImportAst> imports = new ArrayList<ImportAst>();
	
	public List<ImportAst> getImports() {
		return imports;
	}

	public void setImports(List<ImportAst> imports) {
		this.imports = imports;
	}

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
