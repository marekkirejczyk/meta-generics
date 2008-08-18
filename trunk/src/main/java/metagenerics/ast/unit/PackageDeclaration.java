package metagenerics.ast.unit;

import java.util.ArrayList;
import java.util.List;

import metagenerics.ast.Node;
import metagenerics.ast.Visitor;

public class PackageDeclaration extends Node {
	List<String> packagePath = new ArrayList<String>();

	public boolean add(String aPackage) {
		return packagePath.add(aPackage);
	}

	public List<String> getPackagePath() {
		return packagePath;
	}

	public void setPackagePath(List<String> packagePath) {
		this.packagePath = packagePath;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
