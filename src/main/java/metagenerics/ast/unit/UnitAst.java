package metagenerics.ast.unit;

import java.util.ArrayList;
import java.util.List;

import metagenerics.ast.Node;
import metagenerics.ast.common.Annotations;
import metagenerics.ast.declarations.Element;
import metagenerics.ast.declarations.Elements;
import metagenerics.ast.metageneric.MetaDeclaration;

public class UnitAst extends Node {
	
	PackageDeclaration packageDeclaration = new PackageDeclaration();
	
	List<ImportAst> imports = new ArrayList<ImportAst>();

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

}
