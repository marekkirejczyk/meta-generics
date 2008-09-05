package metagenerics.pipe.phase2;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import metagenerics.ast.metageneric.MetaGenericAst;
import metagenerics.exception.CompileException;
import metagenerics.pipe.common.CustomDirClassLoader;
import metagenerics.runtime.MetaGeneric;

public class MetaGenericProxyLoader {
	private String intermediateFolder;

	public String getIntermediateFolder() {
		return intermediateFolder;
	}

	public void setIntermediateFolder(String intermediateFolder) {
		this.intermediateFolder = intermediateFolder;
	}

	public MetaGeneric load(MetaGenericAst ast) {
		try {
			return loadStrict(ast);
		} catch (IOException e) {
			throw new CompileException(e);
		} catch (InstantiationException e) {
			throw new CompileException(e);
		} catch (IllegalAccessException e) {
			throw new CompileException(e);
		} catch (ClassNotFoundException e) {
			throw new CompileException(e);
		} catch (IllegalArgumentException e) {
			throw new CompileException(e);
		} catch (SecurityException e) {
			throw new CompileException(e);
		} catch (InvocationTargetException e) {
			throw new CompileException(e);
		} catch (NoSuchMethodException e) {
			throw new CompileException(e);
		}
	}

	public MetaGeneric loadStrict(MetaGenericAst ast) throws IOException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, IllegalArgumentException,
			SecurityException, InvocationTargetException, NoSuchMethodException {
		String klass = ast.getSymbol().getCannonicalName() + "Factory";
		CustomDirClassLoader loader = new CustomDirClassLoader(
				intermediateFolder);
		Class<? extends Object> genericClass = loader.loadClass(klass);
  		Object factory = genericClass.newInstance();
		return (MetaGeneric) genericClass.getMethod("create").invoke(factory);
	}
}
