package metagenerics.visitors;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import metagenerics.ast.VisitorBase;
import metagenerics.ast.declarations.AnnotationDeclaration;
import metagenerics.ast.declarations.ClassDeclaration;
import metagenerics.ast.declarations.Element;
import metagenerics.ast.declarations.EnumDeclaration;
import metagenerics.ast.declarations.Interface;
import metagenerics.ast.metageneric.MetaGenericAst;
import metagenerics.ast.metageneric.MetaTypedefAst;
import metagenerics.ast.unit.UnitAst;
import metagenerics.transform.metageneric.MetaGenericCompiler;
import metagenerics.transform.metageneric.MetaGenericTransform;


public class MetaGenericBuilder extends VisitorBase {

	MetaGenericCompiler compiler = new MetaGenericCompiler();

	MetaGenericTransform transfom = new MetaGenericTransform();

	UnitAst currentUnit;

	private boolean removeFlag = false;

	public String getIntermediateFolder() {
		return compiler.getIntermediateFolder();
	}

	public void setIntermediateFolder(String intermediateFolder) {
		compiler.setIntermediateFolder(intermediateFolder);
	}

	public Map<String, UnitAst> build(Map<String, UnitAst> units) {
		Map<String, UnitAst> copy = new TreeMap<String, UnitAst>();
		for (Map.Entry<String, UnitAst> entry : units.entrySet()) {
			UnitAst clone = entry.getValue().clone();
			copy.put(entry.getKey(), clone);
			clone.accept(this);
		}
		return copy;
	}

	public void visit(UnitAst unit) {
		currentUnit = unit;
		Iterator<Element> i = unit.getElements().getElements().iterator();
		while (i.hasNext()) {
			i.next().accept(this);
			if (removeFlag) {
				i.remove();
				removeFlag = false;
			}
		}
	}

	@Override
	public void visit(AnnotationDeclaration annotation) {
		removeFlag = true;
	}

	@Override
	public void visit(ClassDeclaration classAst) {
		removeFlag = true;
	}

	@Override
	public void visit(EnumDeclaration klass) {
		removeFlag = true;
	}

	@Override
	public void visit(Interface interfaceAst) {
		removeFlag = true;
	}

	@Override
	public void visit(MetaGenericAst metaGenericAst) {
		StringBuilder result = new StringBuilder();
		transfom.transform(metaGenericAst, result);
		metaGenericAst.setTextAfterTransformation(result.toString());
		MetaGenericAst.getStandardImports().addAll(currentUnit.getImports());
	}

	@Override
	public void visit(MetaTypedefAst element) {
		removeFlag = true;
	}

	public void visitUnit(UnitAst unit) {
		currentUnit = unit;
		Collection<MetaGenericAst> elements;
		elements = unit.getElementsByClass(MetaGenericAst.class);
		for (Element element : elements)
			element.accept(this);
	}

	public void visitMetaGeneric(MetaGenericAst metaGenericAst) {
		StringBuilder result = new StringBuilder();
		transfom.transform(metaGenericAst, result);
		metaGenericAst.setTextAfterTransformation(result.toString());
		MetaGenericAst.getStandardImports().addAll(currentUnit.getImports());
		metaGenericAst.setMetagenericInstance(compiler.compile(metaGenericAst));
	}

}
