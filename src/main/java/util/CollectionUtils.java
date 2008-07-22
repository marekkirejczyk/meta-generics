package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CollectionUtils {
	
	static public <T, S> List<T> filterByClass(List<S> elements,
			java.lang.Class<T> klass) {
		List<T> result = new ArrayList<T>();
		for (S element : elements)
			if (element.getClass().equals(klass))
				result.add((T) element);
		return Collections.unmodifiableList(result);
	}

	public static <T> String toString(Collection<T> collection, String separator) {
		return CollectionUtils.toString(collection, separator, "", "");
	}

	public static <T> String toString(Collection<T> collection,
			String separator, String begin, String end) {
		StringBuilder result = new StringBuilder();
		result.append(begin);
		boolean first = true;
		for (T arg : collection) {
			if (first)
				first = false;
			else
				result.append(separator); 
			result.append(arg);
		}
		result.append(end);
		return result.toString();
	}
}
