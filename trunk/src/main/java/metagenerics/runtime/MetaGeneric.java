package metagenerics.runtime;

import java.util.Collection;

import metagenerics.ast.Node;
import metagenerics.ast.declarations.ClassDeclaration;
import metagenerics.ast.member.Constructor;
import metagenerics.ast.member.Method;
import metagenerics.ast.metageneric.MetaTypedefAst;
import metagenerics.pipe.common.PrettyPrinter;

abstract public class MetaGeneric {

	private String extendsName;

	protected abstract void translateMetaGenerics(MetaTypedefAst typedef,
			StringBuilder result);

	abstract public void setArgument(int i, ClassDeclaration arg);

	private StringBuilder output;

	MetaTypedefAst typedef;

	public void generateClass(MetaTypedefAst typedef, StringBuilder result) {
		this.typedef = typedef;
		StringBuilder classBody = new StringBuilder();
		output = classBody;
		translateMetaGenerics(typedef, classBody);

		result.append(typedef.getModifiers().getText());
		result.append(" class ");
		result.append(typedef.getName());
		if (extendsName != null) {
			result.append(" extends " + extendsName);
		}
		result.append(" {\n");
		result.append(classBody);
		result.append("}\n");
	}

	public void evaluate(String text, Object... args) {
		output.append(String.format(text, args));
		output.append("\n");
	}

	public void evaluate(Node node) {
		PrettyPrinter prettyPrinter = new PrettyPrinter();
		prettyPrinter.setAppendable(output);

		if (node instanceof Constructor) {
			Constructor constructor = ((Constructor) node).clone();
			constructor.setName(typedef.getName());
			constructor.accept(prettyPrinter);
		} else {
			node.accept(prettyPrinter);
		}
	}

	public void evaluateWithName(Method method, String name) {
		Method clonedMethod = method.clone();
		clonedMethod.setName(name);
		evaluate(clonedMethod);
	}

	public void setExtends(String name) {
		this.extendsName = name;
	}

	public void evaluateAll(Collection<? extends Node> nodes) {
		for (Node node : nodes)
			evaluate(node);
	}
}
