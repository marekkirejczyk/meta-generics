package metagenerics.ast.member;

import java.util.ArrayList;
import java.util.List;

import util.CollectionUtils;

public class Members {
	List<Member> members = new ArrayList<Member>();

	public void add(Member member) {
		members.add(member);
	}

	public void addAll(VariableBuilder builder) {
		members.addAll(builder.getFields());
	}

	public List<Method> getMethods() {
		return CollectionUtils.filterByClass(members, Method.class);
	}

	public List<Field> getFields() {
		return CollectionUtils.filterByClass(members, Field.class);
	}

	public List<Constructor> getConstructors() {
		return CollectionUtils.filterByClass(members, Constructor.class);
	}

	public Method getMethod(String name) {
		for (Method m: CollectionUtils.filterByClass(members, Method.class))
			if (m.getName().equals(name))
				return m;
		return null;
	}
}
