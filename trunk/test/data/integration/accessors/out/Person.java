

 @interface Getter {}  @interface Setter {}  @interface Accessors {}  class Adress{String street;int houseNo;} class PersonStub{@Getter
  @Setter
  int x;@Accessors
  int y;@Getter
  String name;@Setter
  Adress adress;static public void main(){
	  Person person = new Person();
	  person.setY(7);
	  System.out.println(person.getY());
	  
  }} class AddAccessors extends metagenerics.MetaGeneric {
	public metagenerics.ast.declarations.ClassDeclaration C;

	public void setArgument(int i, metagenerics.ast.declarations.ClassDeclaration arg) {
		switch (i) {
			case 1: this.C = arg;break;
			default: throw new metagenerics.exception.UnexpectedArgumentIndexException(i);
		}
	}

	protected void translateMetaGenerics(metagenerics.ast.metageneric.Typedef typedef, StringBuilder result) { 
{
	    for (metagenerics.ast.Node e: C.getChildren())
	      evaluate(e);

	    for (metagenerics.ast.member.Field m: C.getFields())
	      if (m.hasAnnotation("Getter") || m.hasAnnotation("Accessors"))
	    	evaluate("%1$s get%2$s() {return %3$s;}", m.getType(), util.StringUtils.capitalize(m.getName()), m.getName());

	    for (metagenerics.ast.member.Field m: C.getFields())
	      if (m.hasAnnotation("Setter") || m.hasAnnotation("Accessors"))
	    	evaluate("void set%2$s(%1$s arg) {%3$s = arg;}", m.getType(), util.StringUtils.capitalize(m.getName()), m.getName());
	  }
 } }
public class Person{
@Getter
  @Setter
  int x;@Accessors
  int y;@Getter
  String name;@Setter
  Adress adress;static public void main(){
	  Person person = new Person();
	  person.setY(7);
	  System.out.println(person.getY());
	  
  }int getX() {return x;}
int getY() {return y;}
String getName() {return name;}
void setX(int arg) {x = arg;}
void setY(int arg) {y = arg;}
void setAdress(Adress arg) {adress = arg;}
}
 