package trash;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import metagenerics.ast.metageneric.MetaGeneric;
import metagenerics.ast.metageneric.MetaParameter;
import metagenerics.ast.unit.Unit;
import util.MetaPrint;

class MetaGenericComilation extends MetaElementCompilation {

	MetaGeneric generic;

	String targetDirectory;

	public MetaGenericComilation(File file, String outDir, String baseDir,
			Unit unit, MetaGeneric generic) {
		this.file = file;
		this.outDir = outDir;
		this.unit = unit;
		this.generic = generic;
		this.targetDirectory = baseDir;
	}

	protected String getGeneratedFileName() {
		return outDir + File.separator + generic.getName() + ".java";
	}

	protected void generateMethodBody(PrintStream ps) {
		MetaPrint.metaPrintln(ps, "\"public class \"", "name", "\" {\"");
		/*for (MetaMember member : generic.getMembers())
			if (member instanceof MetaBlock)
				ps.println(member.getText());
			else
				MetaPrint.metaPrintQuotedLines(ps, member.getText());*/
		ps.println("\t\tps.println(\"}\");");
	}

	protected void generateFileHeader(PrintStream ps) {
		ps.println("package " + unit.getPackageDeclaration() + ";\n");
		// ps.println(unit.getImports().getText() + "\n");
		ps.println("import java.io.PrintStream;");
	}

	protected void generateArgsCountMethod(PrintStream ps) {
		//int paramsCount = generic.getParamters().size();
		ps.println("\tpublic int getParameterCount() {");
		//ps.println("\t\treturn " + paramsCount + ";");
		ps.println("\t}\n");
	}

	protected void generateArg(PrintStream ps, MetaParameter param) {
		String name = param.getName();
		ps.println("\tpublic Class " + name + ";\n");
	}

	protected void generateArgs(PrintStream ps) {
		// for (MetaParameter param: generic.getParamters())
		// generateArg(ps, param);

		generateArgsCountMethod(ps);
	}

	private void generateMethodHeader(PrintStream ps) {
		ps.println("	public void generateClass(PrintStream ps, String name) {");
	}

	private void generateClassFooter(PrintStream ps) {
		ps.println("}");
	}

	private void generateMethodFooter(PrintStream ps) {
		ps.println("\t}");
	}

	private void generateClassHeader(PrintStream ps) {
		ps.println("public class " + generic.getName()
				+ " implements metagenerics.Generator {\n");
	}

	protected void generateFile(PrintStream ps) {
		generateFileHeader(ps);

		generateClassHeader(ps);
		generateArgs(ps);
		generateMethodHeader(ps);
		generateMethodBody(ps);
		generateMethodFooter(ps);
		generateClassFooter(ps);
	}

	private void compile() {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		compiler.run(null, null, null, getGeneratedFileName());
	}

	public void generate() throws IOException, ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		FileOutputStream fos = new FileOutputStream(getGeneratedFileName());
		PrintStream ps = new PrintStream(fos);
		generateFile(ps);
		fos.close();
		compile();
	}

}
