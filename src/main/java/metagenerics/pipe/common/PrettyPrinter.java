package metagenerics.pipe.common;

import static util.StringUtils.*;

import java.io.IOException;
import metagenerics.ast.Node;
import metagenerics.ast.Visitor;
import metagenerics.ast.common.Annotation;
import metagenerics.ast.common.Annotations;
import metagenerics.ast.common.Modifiers;
import metagenerics.ast.common.Semicolon;
import metagenerics.ast.declarations.AnnotationDeclaration;
import metagenerics.ast.declarations.ClassDeclaration;
import metagenerics.ast.declarations.Element;
import metagenerics.ast.declarations.EnumDeclaration;
import metagenerics.ast.declarations.Interface;
import metagenerics.ast.member.Block;
import metagenerics.ast.member.Constructor;
import metagenerics.ast.member.Field;
import metagenerics.ast.member.MemberMock;
import metagenerics.ast.member.Method;
import metagenerics.ast.member.VariableBuilder;
import metagenerics.ast.metageneric.MetaGenericAst;
import metagenerics.ast.metageneric.MetaTypedefAst;
import metagenerics.ast.unit.ImportAst;
import metagenerics.ast.unit.PackageDeclaration;
import metagenerics.ast.unit.UnitAst;
import metagenerics.exception.CompileException;

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

	int indentLevel = 0;

	private void incrementIndentLevel() {
		indentLevel++;
	}

	private void decrementIndentLevel() {
		indentLevel--;
	}

	
	private void appendWithIndent(String text) {
		for (int i = 0; i < indentLevel; i++)
			append("\t");
		append(text);
	}

	public Appendable getAppendable() {
		return appendable;
	}

	public void setAppendable(Appendable appendable) {
		this.appendable = appendable;
	}

	public void visit(Annotations annotations) {
		if (annotations.getAnnotations().size() == 0)
			return;
		for (Annotation a: annotations.getAnnotations())
			appendWithIndent(a.getText() + "\n");
	}
	
	public void visit(UnitAst unit) {
		visit(unit.getAnnotations());
		unit.getPackageDeclaration().accept(this);
		append("\n");
		for (ImportAst anImport : unit.getImports())
			anImport.accept(this);
		append("\n");
		for (Element element : unit.getElements().getElements())
			element.accept(this);
	}

	public void visit(PackageDeclaration packageAst) {
		append(packageAst.getText() + "\n");
	}

	public void visit(ImportAst importAst) {
		append("import ");
		if (importAst.isStatic())
			append("static ");
		append(formatCollection(importAst.getIdentifiers(), "."));
		if (importAst.isGeneral())
			append(".*");
		append(";\n");
	}

	public void visit(ClassDeclaration klass) {
		klass.getModifiers().accept(this);
		appendWithIndent("class " + klass.getName());
		if (klass.getGenericParameters() != null)
			append("<"
					+ formatCollection(
							klass.getGenericParameters(), ", ") + ">");

		if (klass.getSuperClass() != null)
			append(" extends " + klass.getSuperClass());
		if (klass.getImplementedInterfaces() != null)
			append(" implements "
					+ formatCollection(klass
							.getImplementedInterfaces(), ", "));
		appendWithIndent("{\n");
		incrementIndentLevel();
		for (Node node : klass.getChildren())
			node.accept(this);
		decrementIndentLevel();
		appendWithIndent("}\n");
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
					+ formatCollection(
							klass.getGenericParameters(), ", ") + ">");

		if (klass.getSuperClass() != null)
			append(" extends " + klass.getSuperClass());
		if (klass.getImplementedInterfaces() != null)
			append(" implements "
					+ formatCollection(klass
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
		appendWithIndent(vb.getText() + "\n\n");
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

	public void visit(Method method) {
		appendWithIndent("");
		method.getModifiers().accept(this);
		if (method.getGenericParameters() != null) {
			append("<");
			append(formatCollection(method.getGenericParameters(), ","));
			append("> ");
		}
		append(method.getType());
		append(" " + method.getName());
		append(translateEvaluateLanguage(method.getRest()) + "\n\n");

	}

	public void visit(Constructor constructor) {
		constructor.getModifiers().accept(this);
		if (constructor.getGenericParameters() != null) {
			append("<");
			append(formatCollection(constructor.getGenericParameters(),
					","));
			append("> ");
		}
		append(constructor.getName());
		append(translateEvaluateLanguage(constructor.getRest()));
	}

	public void visit(Field field) {
		append(field.getType() + " " + field.getName() + field.getRest() + ";");
	}

	public void visit(Block block) {
		boolean isNormalBlock = block.getType().equals(block.getType().NORMAL);

		String modifier = isNormalBlock ? "" : block.getType().name()
				.toLowerCase()
				+ " ";
		append("\t" + modifier);
		append(translateEvaluateLanguage(block.getInstructionBlock().getText()) + "\n");
	}

	public void visit(MemberMock mock) {
		append(mock.getText());
	}

	public void visit(Semicolon semicolonAst) {
		append(";");
	}

	public void visit(Modifiers modifiers) {
		append(modifiers.getText());
		if (modifiers.getText().length() > 0)
			append(" ");
	}

}
