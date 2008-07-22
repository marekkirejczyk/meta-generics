package metagenerics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import metagenerics.ast.Node;
import metagenerics.ast.declarations.ClassDeclaration;
import metagenerics.ast.declarations.Element;
import metagenerics.ast.member.VariableBuilder;
import metagenerics.ast.metageneric.Typedef;
import metagenerics.transform.parse.PrettyPrinter;

abstract public class MetaGeneric {

	Collection<Element> elements = new ArrayList<Element>();

	protected abstract void translateMetaGenerics(Typedef typedef,
			StringBuilder result);

	abstract public void setArgument(int i, ClassDeclaration arg);

	private StringBuilder output;

	public void generateClass(Typedef typedef, StringBuilder result) {
		result.append(typedef.getModifiers().getText());
		result.append(" class ");
		result.append(typedef.getName());
		result.append("{\n");
		output = result;
		translateMetaGenerics(typedef, result);
		result.append("}\n");
	}

	protected void evaluate(String text, Object... args) {
		output.append(String.format(text, args));
		output.append("\n");
	}

	protected void doEvaluate(Node node) throws IOException {
		PrettyPrinter prettyPrinter = new PrettyPrinter();
		prettyPrinter.setAppendable(output);
		node.accept(prettyPrinter);
		//prettyPrinter.visit(walk((VariableBuilder) node);
	}

	protected void evaluate(Node node) {
		try {
			doEvaluate(node);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
