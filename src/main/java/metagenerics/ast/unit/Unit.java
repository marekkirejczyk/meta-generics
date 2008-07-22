package metagenerics.ast.unit;

import java.util.ArrayList;
import java.util.List;

import metagenerics.ast.Node;
import metagenerics.ast.common.Annotations;
import metagenerics.ast.declarations.Element;
import metagenerics.ast.declarations.Elements;
import metagenerics.ast.metageneric.MetaDeclaration;

public class Unit extends Node {

	PackageDeclaration packageDeclaration = new PackageDeclaration();

	Imports imports = new Imports();

	Elements elements = new Elements();

	Annotations annotations = new Annotations();

	List<MetaDeclaration> metaElements = new ArrayList<MetaDeclaration>();

	public PackageDeclaration getPackageDeclaration() {
		return packageDeclaration;
	}

	public void setPackageDeclaration(PackageDeclaration packageDeclaration) {
		this.packageDeclaration = packageDeclaration;
	}

	public void add(Element element) {
		if (element != null)
			elements.add(element);
	}

	public Imports getImports() {
		return imports;
	}

	public void setImports(Imports imports) {
		this.imports = imports;
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

}
