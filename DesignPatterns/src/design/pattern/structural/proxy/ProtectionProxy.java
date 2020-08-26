package design.pattern.structural.proxy;

interface Drivable
{
  void drive();
}

class Car implements Drivable
{
  protected Driver driver;

  public Car(Driver driver)
  {
    this.driver = driver;
  }

  @Override
  public void drive()
  {
    System.out.println("Car being driven");
  }
}

class CarProxy extends Car
{
  private Driver driver;

  public CarProxy(Driver driver)
  {
    super(driver);
  }

  @Override //Proxy controlling driver age. Similar to car but has some checks.
  public void drive()
  {
    if (driver.age >= 17)
      super.drive();
    else
      System.out.println("Driver too young");
  }
}

class Driver
{
  public int age;

  public Driver(int age)
  {
    this.age = age;
  }
}

class ProtectionProxy
{
  public static void main(String[] args)
  {
    Drivable car = new CarProxy(new Driver(12)); // 22
    car.drive();
  }
}
