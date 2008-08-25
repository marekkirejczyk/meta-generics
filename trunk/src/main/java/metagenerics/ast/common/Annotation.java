package metagenerics.ast.common;

import java.util.ArrayList;
import java.util.List;

import metagenerics.ast.Node;

public class Annotation extends Node {
	String name;

	List<Argument> arguments = new ArrayList<Argument>();

	public List<Argument> getArguments() {
		return arguments;
	}

	public void setArguments(List<Argument> arguments) {
		this.arguments = arguments;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getArgumentText(String name) {
		for (Argument argument: getArguments())
			if (argument.getType().equals(name))
				return argument.getName();
		return null;
	}
	
	public String getStringArgument(String name) {
		String arg = getArgumentText(name);
		return arg.substring(1, arg.length()-1);
	}
}
