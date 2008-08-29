package metagenerics.ast.unit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import metagenerics.ast.Node;
import metagenerics.ast.Visitor;
import metagenerics.ast.common.Annotations;
import metagenerics.ast.declarations.Element;
import metagenerics.ast.declarations.Elements;
import metagenerics.ast.metageneric.MetaDeclaration;
import util.CollectionUtils;

public class UnitAst extends Node {

	PackageDeclaration packageDeclaration = new PackageDeclaration();

	List<ImportAst> imports = new ArrayList<ImportAst>();

	Elements elements = new Elements();

	Annotations annotations = new Annotations();

	List<MetaDeclaration> metaElements = new ArrayList<MetaDeclaration>();

	public UnitAst() {

	}

	protected UnitAst(UnitAst copy) {
		super(copy);
		packageDeclaration = copy.packageDeclaration;
		imports = copy.imports;
		elements = copy.elements.clone();
		annotations = copy.annotations;
		metaElements = copy.metaElements;
	}

	public PackageDeclaration getPackageDeclaration() {
		return packageDeclaration;
	}

	public void setPackageDeclaration(PackageDeclaration packageDeclaration) {
		this.packageDeclaration = packageDeclaration;
	}

	public void add(Element element) {
		if (element == null)
			return;
		elements.add(element);
	}

	public void remove(Element element) {
		elements.remove(element);
	}

	
	public Elements getElements() {
		return elements;
	}

	public void setElements(Elements elements) {
		this.elements = elements;
	}

	public Annotations getAnnotations() {
		return annotations;
	}

	public void setAnnotations(Annotations annotations) {
		this.annotations = annotations;
	}

	public List<MetaDeclaration> getMetaElements() {
		return metaElements;
	}

	public void setMetaElements(List<MetaDeclaration> metaElements) {
		this.metaElements = metaElements;
	}

	public List<ImportAst> getImports() {
		return imports;
	}

	public void setImports(List<ImportAst> imports) {
		this.imports = imports;
	}

	public boolean containsImport(ImportAst importAst) {
		for (ImportAst anImport : getImports())
			if (anImport.equals(importAst))
				return true;
		return false;
	}

	public void addAllImports(List<ImportAst> imports) {
		for (ImportAst anImport : imports)
			if (!containsImport(anImport))
				getImports().add(anImport);
	}

	public <T extends Element> Collection<T> getElementsByClass(Class<T> aClass) {
		return CollectionUtils.filterByClass(elements.getElements(), aClass);
	}

	@Override
	public UnitAst clone() {
		return new UnitAst(this);
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
	
	

}
