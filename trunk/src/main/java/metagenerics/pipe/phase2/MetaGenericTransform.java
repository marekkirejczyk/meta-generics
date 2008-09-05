package metagenerics.pipe.phase2;

import metagenerics.ast.Node;
import metagenerics.ast.common.Modifiers;
import metagenerics.ast.declarations.Element;
import metagenerics.ast.member.Block;
import metagenerics.ast.metageneric.MetaGenericAst;
import metagenerics.pipe.common.PrettyPrinter;
import static util.StringUtils.*;

public class MetaGenericTransform {

	final static String classAstType = "metagenerics.ast.declarations.ClassDeclaration";

	final static String metaTypedefAstType = "metagenerics.ast.metageneric.MetaTypedefAst";

	final static String metaGenericAstType = "metagenerics.runtime.MetaGeneric";

	boolean useOriginalModifiers = true;

	public boolean isUseOriginalModifiers() {
		return useOriginalModifiers;
	}

	public void setUseOriginalModifiers(boolean useOriginalModifiers) {
		this.useOriginalModifiers = useOriginalModifiers;
	}

	protected void generateModifiers(MetaGenericAst metaGeneric,
			StringBuilder result) {
		if (useOriginalModifiers)
			result.append(metaGeneric.getModifiers().getText());
		else
			result.append("public");
	}

	MetaGenericAst metaGeneric;

	StringBuilder result;

	protected void appendMetaText(String text) {
		result.append(translateEvaluateLanguage(text));
	}

	protected void appendBlock(Block block) {
		if (block.getType() == Block.Type.META)
			appendMetaText(block.getInstructionBlock().getText());
		else
			appendElement(block);
	}

	protected void generateParameters(MetaGenericAst metaGeneric,
			StringBuilder result) {
		int i = 1;

		for (String arg : metaGeneric.getGenericParameters())
			result.append(String.format("\tpublic %1$s %2$s;\n", classAstType,
					arg));
		result.append("\n\tpublic void setArgument(int i, " + classAstType
				+ " arg) {\n");
		result.append("\t\tswitch (i) {\n");

		for (String arg : metaGeneric.getGenericParameters()) {
			result.append(String.format(
					"\t\t\tcase %1$s: this.%2$s = arg;break;\n", i, arg));
		}
		result
				.append("\t\t\tdefault: throw new metagenerics.exception.UnexpectedArgumentIndexException(i);\n");
		result.append("\t\t}\n\t}\n");

	}

	boolean isMetaElement(Node node) {
		if (!(node instanceof Element))
			return false;
		Element element = (Element) node;
		Modifiers modififers = element.getModifiers();
		return modififers.containsAnnotation("Meta");
	}

	void appendElement(Node node) {
		String text = node.getText().replaceAll("\n", "");
		text = escapeCharacters(text);
		result.append("evaluate(\"" + text + "\");");
	}

	void appendMetaElement(Element element) {
		StringBuilder partialResult = new StringBuilder();
		element.accept(new PrettyPrinter(partialResult));
		appendMetaText(partialResult.toString());
	}

	void appendMetaElements() {
		for (Node node : metaGeneric.getChildren())
			if (isMetaElement(node))
				appendMetaElement((Element) node);
	}

	void appendElements() {
		result.append("\n\tprotected void translateMetaGenerics("
				+ metaTypedefAstType + " typedef, StringBuilder result) { \n");

		for (Node node : metaGeneric.getChildren())
			if (node instanceof Block)
				appendBlock((Block) node);
			else if (!(isMetaElement(node)))
				appendElement(node);

		result.append("\t}\n");
	}

	public void transform(MetaGenericAst metaGeneric, StringBuilder result) {
		this.metaGeneric = metaGeneric;
		this.result = result;

		generateModifiers(metaGeneric, result);

		result.append(" class " + metaGeneric.getName() + " extends "
				+ metaGenericAstType + " {\n");

		generateParameters(metaGeneric, result);
		appendMetaElements();
		appendElements();
		result.append("}\n");
	}
}
