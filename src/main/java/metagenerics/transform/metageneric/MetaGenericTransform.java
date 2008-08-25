package metagenerics.transform.metageneric;

import util.StringUtils;
import metagenerics.ast.Node;
import metagenerics.ast.member.Block;
import metagenerics.ast.metageneric.MetaGenericAst;

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

	public void transform(MetaGenericAst metaGeneric, StringBuilder result) {
		if (useOriginalModifiers)
			result.append(metaGeneric.getModifiers().getText());
		else
			result.append("public");

		result.append(" class " + metaGeneric.getName()
				+ " extends " + metaGenericAstType + " {\n");
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

		result
				.append("\n\tprotected void translateMetaGenerics(" + metaTypedefAstType +" typedef, StringBuilder result) { \n");

		for (Node node : metaGeneric.getChildren())
			if (node instanceof Block) {
				Block block = (Block) node;
				if (block.getType() == Block.Type.META)
					result.append(block.getInstructionBlock().getText());
			} else {
				String text = node.getText().replaceAll("\n", "");
				text = StringUtils.escapeCharacters(text);
				result.append("evaluate(\""
						+ text + "\");");
			}

		result.append("\n } }\n");

	}
}
