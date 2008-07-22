package metagenerics.ast.common;

import java.util.Map;
import java.util.TreeMap;

import metagenerics.ast.Node;

public class Annotation extends Node {
	
	String name;
	
	Map<String, String> args = new TreeMap<String, String>();

	public boolean containsArgument(String arg0) {
		return args.containsKey(arg0);
	}

	public String getArgument(String arg0) {
		return args.get(arg0);
	}

	public String addArgument(String arg0, String arg1) {
		return args.put(arg0, arg1);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
