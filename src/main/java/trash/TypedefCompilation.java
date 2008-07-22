package trash;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;


import metagenerics.MetaGeneric;
import metagenerics.ast.metageneric.Typedef;
import metagenerics.ast.unit.Unit;
import metagenerics.transform.javacompile.CustomDirClassLoader;

public class TypedefCompilation extends MetaElementCompilation {

	Typedef metaTypeDef;

	String targetDirectory;

	public TypedefCompilation(File file, String outDir, String baseDir,
			Unit unit, Typedef metaTypeDef) {
		this.file = file;
		this.outDir = outDir;
		this.unit = unit;
		this.metaTypeDef = metaTypeDef;
		this.targetDirectory = baseDir;
	}

	public String getMetagenericPath() {
		return unit.getPackageDeclaration() + "."
				+ metaTypeDef.getFunction();
	}

	public String getFileName() {
		return outDir + File.separator + metaTypeDef.getName() + ".java";
	}

	void generateHeader(PrintStream ps) {
		ps.println("package " + unit.getPackageDeclaration() + ";\n");
		//ps.println(unit.getImports() + "\n");
	}

	protected void assignParameters(MetaGeneric g) {
		/*if (g.getParameterCount() != metaTypeDef.getArgs().size())
			throw new WrongParameterCount();
		int i = 0;
		
		for (MetaParameter parameter : metaTypeDef.getArgs()) {
			Field f = g.getClass().getFields()[i++];
			ClassLoader loader = new CustomDirClassLoader(targetDirectory);
			try {
				String name = parameter.getName();
				Class o = loader.loadClass(name);
				f.set(g, o);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		*/
	}

	void generateClass(PrintStream ps) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		ClassLoader loader = new CustomDirClassLoader(targetDirectory);
		MetaGeneric g = (MetaGeneric) loader.loadClass(getMetagenericPath())
				.newInstance();

		assignParameters(g);

		//g.generateClass(ps, metaTypeDef.getName());
	}

	void generate() throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, IOException {
		System.out.println(getFileName());
		FileOutputStream os = new FileOutputStream(getFileName());
		PrintStream ps = new PrintStream(os);
		generateHeader(ps);
		generateClass(ps);
		os.close();
	}

}
