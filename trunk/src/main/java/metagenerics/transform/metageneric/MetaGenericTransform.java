package metagenerics.transform.metageneric;

import metagenerics.ast.Node;
import metagenerics.ast.member.Block;
import metagenerics.ast.metageneric.MetaGeneric;

public class MetaGenericTransform {
	final static String type = "metagenerics.ast.declarations.ClassDeclaration";

	boolean useOriginalModifiers = true;

	public boolean isUseOriginalModifiers() {
		return useOriginalModifiers;
	}

	public void setUseOriginalModifiers(boolean useOriginalModifiers) {
		this.useOriginalModifiers = useOriginalModifiers;
	}

	public void transform(MetaGeneric metaGeneric, StringBuilder result) {
		if (useOriginalModifiers)
			result.append(metaGeneric.getModifiers().getText());
		else
			result.append("public");

		result.append(" class " + metaGeneric.getName()
				+ " extends metagenerics.MetaGeneric {\n");
		int i = 1;
		for (String arg : metaGeneric.getGenericParameters())
			result.append(String.format("\tpublic %1$s %2$s;\n", type, arg));

		result.append("\n\tpublic void setArgument(int i, " + type
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
				.append("\n\tprotected void translateMetaGenerics(metagenerics.ast.metageneric.Typedef typedef, StringBuilder result) { \n");

		for (Node node : metaGeneric.getChildren())
			if (node instanceof Block) {
				Block block = (Block) node;
				if (block.getType() == Block.Type.META)
					result.append(block.getInstructionBlock().getText());
			} else {

				result.append("evaluate(\""
						+ node.getText().replaceAll("\n", "") + "\");");
			}

		result.append("\n } }\n");

	}
}
