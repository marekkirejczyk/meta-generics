package metagenerics.transform.parse;

import java.io.IOException;

import metagenerics.ast.Node;
import metagenerics.ast.Visitor;
import metagenerics.ast.common.Semicolon;
import metagenerics.ast.declarations.AnnotationDeclaration;
import metagenerics.ast.declarations.ClassDeclaration;
import metagenerics.ast.declarations.Element;
import metagenerics.ast.declarations.EnumDeclaration;
import metagenerics.ast.declarations.Interface;
import metagenerics.ast.member.Block;
import metagenerics.ast.member.Field;
import metagenerics.ast.member.MemberMock;
import metagenerics.ast.member.VariableBuilder;
import metagenerics.ast.metageneric.MetaGenericAst;
import metagenerics.ast.metageneric.MetaTypedefAst;
import metagenerics.ast.unit.ImportAst;
import metagenerics.ast.unit.PackageDeclaration;
import metagenerics.ast.unit.UnitAst;
import metagenerics.exception.CompileException;
import util.StringUtils;

public class PrettyPrinter implements Visitor {

	Appendable appendable = System.out;

	public PrettyPrinter() {
	}

	public PrettyPrinter(Appendable appendable) {
		this.appendable = appendable;
	}

	private void append(String text) {
		try {
			appendable.append(text);
		} catch (IOException e) {
			throw new CompileException(e);
		}
	}

	public Appendable getAppendable() {
		return appendable;
	}

	public void setAppendable(Appendable appendable) {
		this.appendable = appendable;
	}

	public void visit(UnitAst unit) {
		append(unit.getAnnotations().getText() + "\n");
		unit.getPackageDeclaration().accept(this);
		for (ImportAst anImport : unit.getImports())
			anImport.accept(this);
		for (Element element : unit.getElements().getElements())
			element.accept(this);
	}

	public void visit(PackageDeclaration packageAst) {
		append(packageAst.getText() + "\n");
	}

	public void visit(ImportAst importAst) {
		append("import ");
		if (importAst.isStatic())
			append(" static ");
		append(StringUtils.formatCollection(importAst.getIdentifiers(), "."));
		if (importAst.isGeneral())
			append(".*");
		append(";\n");
	}

	public void visit(ClassDeclaration klass) {
		append(klass.getModifiers().getText() + " " + "class "
				+ klass.getName());
		if (klass.getGenericParameters() != null)
			append("<"
					+ StringUtils.formatCollection(
							klass.getGenericParameters(), ", ") + ">");

		if (klass.getSuperClass() != null)
			append(" extends " + klass.getSuperClass());
		if (klass.getImplementedInterfaces() != null)
			append(" implements "
					+ StringUtils.formatCollection(klass
							.getImplementedInterfaces(), ", "));
		append("{");
		for (Node node : klass.getChildren())
			node.accept(this);
		append("}");
	}

	public void visit(Interface klass) {
		append(klass.getModifiers().getText() + " ");
		append(klass.getText() + " ");
	}

	public void visit(EnumDeclaration klass) {
		append(klass.getModifiers().getText() + " ");
		append(klass.getText() + " ");
	}

	public void visit(AnnotationDeclaration klass) {
		append(klass.getModifiers().getText() + " ");
		append(klass.getText() + " ");
	}

	public void visit(MetaGenericAst klass) {
		if (klass.getTextAfterTransformation() != null) {
			append(klass.getTextAfterTransformation());
			return;
		}
		append(klass.getModifiers().getText() + " " + "meta class "
				+ klass.getName());
		if (klass.getGenericParameters() != null)
			append("<"
					+ StringUtils.formatCollection(
							klass.getGenericParameters(), ", ") + ">");

		if (klass.getSuperClass() != null)
			append(" extends " + klass.getSuperClass());
		if (klass.getImplementedInterfaces() != null)
			append(" implements "
					+ StringUtils.formatCollection(klass
							.getImplementedInterfaces(), ", "));

		append(" {\n");
		for (Node node : klass.getChildren())
			node.accept(this);
		append("}\n");

	}

	public void visit(MetaTypedefAst element) {
		append(element.getText() + " ");
	}

	public void visit(VariableBuilder vb) {
		append(vb.getText());
		/*
		 * append(vb.getAnnotations().getText() + "\n");
		 * append(vb.getModifiers().getText() + " "); append(vb.getType() + "
		 * ");
		 * 
		 * boolean first = true;
		 * 
		 * for (Field f : vb.getFields()) { if (first) first = false; else
		 * append(", "); append(f.getName()); } append(";");
		 */
	}

	public void visit(Field field) {
		append(field.getType() + " " + field.getName() + ";");
	}

	public void visit(Block block) {
		boolean isNormalBlock = block.getType().equals(block.getType().NORMAL);

		String modifier = isNormalBlock ? "" : block.getType().name()
				.toLowerCase()
				+ " ";
		append("\t" + modifier);
		append(block.getInstructionBlock().getText() + "\n");
	}

	public void visit(MemberMock mock) {
		append(mock.getText());
	}

	public void visit(Semicolon semicolonAst) {
		append(";");
	}

}