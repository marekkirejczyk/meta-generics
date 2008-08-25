package metagenerics.ast.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import metagenerics.ast.Node;

public class Annotations extends Node implements Iterable<Annotation> {
	
	List<Annotation> annotations = new ArrayList<Annotation>();

	public boolean addAnnotation(Annotation arg0) {
		return annotations.add(arg0);
	}

	public Iterator<Annotation> iterator() {
		return annotations.iterator();
	}

	public boolean containsAnnotation(String arg) {
		for (Annotation annotation : annotations)
			if (annotation.getName().equals(arg))
				return true;
		return false;
	}
	
	public Annotation getAnnotation(String arg) {
		for (Annotation annotation : annotations)
			if (annotation.getName().equals(arg))
				return annotation;
		return null;
	}
	
	
}
