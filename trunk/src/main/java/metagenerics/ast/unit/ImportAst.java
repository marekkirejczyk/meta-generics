package metagenerics.ast.unit;

import java.util.ArrayList;
import java.util.List;

import util.StringUtils;

import metagenerics.ast.Node;
import metagenerics.ast.Visitor;

public class ImportAst extends Node {

	List<String> identifiers = new ArrayList<String>();

	boolean isStatic = false;

	boolean isGeneral = false;

	public ImportAst() {
		
	}

	public ImportAst(String path, boolean isGeneral) {
		for (String element: path.split("\\."))
			addIdentifier(element);
		this.isGeneral = isGeneral;
	}
	
	public boolean isGeneral() {
		return isGeneral;
	}

	public void setGeneral(boolean isGeneral) {
		this.isGeneral = isGeneral;
	}

	public boolean isStatic() {
		return isStatic;
	}

	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}

	public void addIdentifier(String identifier) {
		identifiers.add(identifier);
	}

	public List<String> getIdentifiers() {
		return identifiers;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public String getPath() {
		return StringUtils.formatCollection(identifiers, ".");
	}	
	
}
