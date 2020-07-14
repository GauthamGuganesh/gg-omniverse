package design.pattern.creational.prototype;

class Address
{
  public String streetAddress, city, country;

  public Address(String streetAddress, String city, String country)
  {
    this.streetAddress = streetAddress;
    this.city = city;
    this.country = country;
  }

  public Address(Address other) //Copy Contructor doing deep copy.
  {
    this(other.streetAddress, other.city, other.country); // Deep copying.
  }

  @Override
  public String toString()
  {
    return "Address{" +
      "streetAddress='" + streetAddress + '\'' +
      ", city='" + city + '\'' +
      ", country='" + country + '\'' +
      '}';
  }
}

class Employee
{
  public String name;
  public Address address;

  public Employee(String name, Address address)
  {
    this.name = name;
    this.address = address;
  }

  public Employee(Employee other)
  {
    name = other.name;
    address = new Address(other.address); // Deep copying to cut reference with passed object.
  }

  @Override
  public String toString()
  {
    return "Employee{" +
      "name='" + name + '\'' +
      ", address=" + address +
      '}';
  }
}

class CopyConstructorDemo
{
  public static void main(String[] args)
  {
    Employee john = new Employee("John",
      new Address("123 London Road", "London", "UK"));

    //Employee chris = john;
    Employee chris = new Employee(john);

    chris.name = "Chris";
    System.out.println(john);
    System.out.println(chris);
  }
}