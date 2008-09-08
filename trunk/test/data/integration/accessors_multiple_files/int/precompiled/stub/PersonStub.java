package stub;

import model.Adress;
import framework.*;

public class PersonStub{
	@Getter
  @Setter
  int x;

	@Accessors
  int y;

	@Getter
  String name;

	@Setter
  Adress adress;

}
