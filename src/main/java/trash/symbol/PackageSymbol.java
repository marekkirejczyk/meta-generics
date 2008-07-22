package trash.symbol;

import java.util.ArrayList;
import java.util.List;

public class PackageSymbol {
	List<ComplexSymbol> types = new ArrayList<ComplexSymbol>();
	
	List<PackageSymbol> child = new ArrayList<PackageSymbol>();
	
	PackagePath myPath;
	
	PackageSymbol parent;
	
}
