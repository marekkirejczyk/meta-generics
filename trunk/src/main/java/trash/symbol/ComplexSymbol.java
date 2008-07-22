package trash.symbol;

public class ComplexSymbol {
	String packagePath;
	
	String name;

	PackageSymbol packagie;
	
	public String getFullName() {
		return packagePath + "." + name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPackagePath() {
		return packagePath;
	}

	public void setPackagePath(String packagePath) {
		this.packagePath = packagePath;
	}

	public PackageSymbol getPackage() {
		return packagie;
	}

	public void setPackage(PackageSymbol packagie) {
		this.packagie = packagie;
	}
	
	
}
