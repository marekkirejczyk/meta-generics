class Adress {
  String street;
  int houseNo;
}




class Person
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

  int getX() {
    return x;
  }
  
  int getY() {
    return y;
  }
  
  String getName() {
    return name;
  }
  
  void setX(int arg) {
    x = arg;
  }
  
  void setY(int arg) {
    y = arg;
  }
  
  void setAdress(Adress arg) {
    adress = arg;
  }
}

