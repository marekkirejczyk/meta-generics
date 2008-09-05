package metagenerics.pipe.phase2;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import metagenerics.ast.Node;
import metagenerics.ast.UnitAsts;
import metagenerics.ast.declarations.ClassDeclaration;
import metagenerics.ast.declarations.Element;
import metagenerics.ast.member.Method;
import metagenerics.ast.metageneric.MetaGenericAst;
import metagenerics.ast.unit.ImportAst;
import metagenerics.ast.unit.PackageDeclaration;
import metagenerics.ast.unit.UnitAst;
import metagenerics.runtime.MetaGeneric;
import metagenerics.symbol.Symbol;
import util.PathUtils;

public class MetaGenericTransformations {

	String factoryName(Map.Entry<String, UnitAst> entry) {
		return entry.getKey() + "Factory";
	}

	static UnitAst createFactoryAst(MetaGenericAst metageneric,
			UnitAst metaGenericUnit) {
		UnitAst unit = new UnitAst();
		ClassDeclaration factoryAst = new ClassDeclaration();
		Method methodAst = new Method();
		factoryAst.getModifiers().setInfo(null, null, "public");
		factoryAst.setName(metageneric.getName() + "Factory");
		methodAst.setName("create");
		methodAst.setType(metageneric.getName());
		methodAst.getModifiers().setInfo(null, null, "static public");
		methodAst.setRest("() {\n\t\treturn new " + metageneric.getName()
				+ "();\n\t}");
		factoryAst.getChildren().add(methodAst);
		PackageDeclaration pack = new PackageDeclaration();
		pack.setInfo(null, null, metaGenericUnit.getPackageDeclaration()
				.getText());
		pack.setPackagePath(metaGenericUnit.getPackageDeclaration()
				.getPackagePath());
		unit.setPackageDeclaration(pack);

		unit.add(factoryAst);
		return unit;
	}

	static String factoryName(String unitName, MetaGenericAst metageneric) {
		String path = PathUtils.stripFileName(unitName);
		return path + "/" + metageneric.getName() + "Factory.java";
	}

	static public void addMetagenericsFactories(UnitAsts units) {
		UnitAsts newUnits = new UnitAsts(new TreeMap<String, UnitAst>());
		for (Map.Entry<String, UnitAst> entry : units.entrySet()) {
			UnitAst unit = entry.getValue();
			for (Element e : unit.getElementsByClass(MetaGenericAst.class)) {
				MetaGenericAst metageneric = (MetaGenericAst) e;
				String name = factoryName(entry.getKey(), metageneric);
				newUnits.put(name, createFactoryAst(metageneric, unit));
			}
		}
		units.putAll(newUnits);
	}

	static private boolean isMetaElement(Element element) {
		return (element instanceof MetaGenericAst)
				|| (element.getModifiers().containsAnnotation("Meta"));
	}

	static void transformMetaGenericIntoClassText(MetaGenericAst metaGenericAst) {
		StringBuilder result = new StringBuilder();
		MetaGenericTransform transfom = new MetaGenericTransform();
		transfom.transform(metaGenericAst, result);
		metaGenericAst.setTextAfterTransformation(result.toString());
	}

	static public void transformMetaGenericsIntoClasses(UnitAsts units) {
		for (UnitAst unit : units.values())
			for (Element e : unit.getElementsByClass(MetaGenericAst.class))
				transformMetaGenericIntoClassText((MetaGenericAst) e);
	}

	static public void filterNonMetaElements(UnitAsts units) {
		for (UnitAst unit : units.values()) {
			Iterator<Element> i = unit.getElements().getElements().iterator();
			while (i.hasNext())
				if (!isMetaElement(i.next()))
					i.remove();
		}
	}

	static void filterImport(Iterator<ImportAst> i, ImportAst anImport) {
		Symbol targetSymbol = anImport.getSymbol();
		if (targetSymbol == null)
			return;

		Node targetNode = targetSymbol.getAstNode();
		boolean isElement = targetNode instanceof Element;
		if (!isElement || !isMetaElement((Element) targetNode))
			i.remove();
	}

	static public void filterNonMetaImports(UnitAsts units) {
		int id = 0;
		for (UnitAst unit : units.values()) {
			ClassDeclaration classAst = new ClassDeclaration();
			classAst.setName("MetagenericCompilerMarker" + id++);
			unit.getElements().getElements().add(classAst);

			Iterator<ImportAst> i = unit.getImports().iterator();
			while (i.hasNext()) {
				ImportAst anImport = (ImportAst) i.next();
				if (!anImport.isStatic() && !anImport.isGeneral()) {
					filterImport(i, anImport);
				} else if (!anImport.isStatic() && anImport.isGeneral()) {

				} else if (anImport.isStatic() && !anImport.isGeneral()) {

				} else if (anImport.isStatic() && anImport.isGeneral()) {
					filterImport(i, anImport);
				}

			}

		}
	}

	static public void addCompliedMetagenericsToAst(String folder, UnitAsts units) {
		MetaGenericProxyLoader compiler = new MetaGenericProxyLoader();
		compiler.setIntermediateFolder(folder);
		for (UnitAst unit : units.values())
			for (Element element : unit
					.getElementsByClass(MetaGenericAst.class)) {
				MetaGenericAst metaGenericAst = (MetaGenericAst) element;
				MetaGeneric aClass = compiler.load(metaGenericAst);
				metaGenericAst.setMetagenericInstance(aClass);
			}
	}
}
