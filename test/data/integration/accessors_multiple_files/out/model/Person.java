package model;

import framework.*;
import stub.PersonStub;

public class Person
{
  @Getter
  @Setter
  int x;

  @Accessors
  int y;

  @Getter
  String name;

  @Setter
  Adress adress;

  public int getX() {
    return x;
  }
  
  public int getY() {
    return y;
  }
  
  public String getName() {
    return name;
  }
  
  public void setX(int arg) {
    x = arg;
  }
  
  public void setY(int arg) {
    y = arg;
  }
  
  public void setAdress(Adress arg) {
    adress = arg;
  }
}

