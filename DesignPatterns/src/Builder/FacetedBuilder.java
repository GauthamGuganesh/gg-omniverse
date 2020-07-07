package Builder;

class Citizen
{
	protected String name;
	protected String streetName, zipCode;
	protected String company, position, salary;
	
	protected String getString() {
		return "Citizen [name=" + name + ", streetName=" + streetName + ", zipCode=" + zipCode + ", company=" + company
				+ ", position=" + position + ", salary=" + salary + "]";
	}
}

//When a derived class is created, a corresponding base class is created. Hence we use super() to sync all derived class
//with base classes.
class CitizenBuilder
{
	public CitizenBuilder()
	{
		
	}
	
	protected Citizen citizen = new Citizen(); //First, declare the object to be built.

	public CitizenBuilder(String name)
	{
		citizen.name = name;
	}
	
	public Addressbuilder lives()
	{
		//When derived class instance created, only derived class instance is created. No instance for base class.
		//Properties and methods are inherited but objects are created for only derived class.
		//So thats why we generally we use super() inside derived class constructor to create a base class.
		//So if super is called, new citizen objects are created everytime. So to stick to the only object we are building
		//we send in citizen object to the derived builders.
		
		//When creating the derived class object, the base class constructor super() is called, which in essence is the same object
		//as the derived class. Has same hashcode.
		
		return new Addressbuilder(citizen); // To ensure that same reference of citizen is passed to all faceted builders.
	}	
	
	public WorkBuilder worksAt()
	{
		return new WorkBuilder(citizen); // To ensure that same reference of citizen is passed to all faceted builders.
	}
	
	public String build() {
		
		return citizen.getString();
	}
	
}

class Addressbuilder extends CitizenBuilder //Only if extended, possible to jump between builders fluently
{
	public Addressbuilder(Citizen citizen) {
		this.citizen = citizen; 
	}

	public Addressbuilder in(String streetName)
	{
		this.citizen.streetName = streetName;
		return this;
	}
	
	public Addressbuilder at(String zipCode)
	{
		this.citizen.zipCode = zipCode;
		return this;
	}
}

class WorkBuilder extends CitizenBuilder
{
	public WorkBuilder(Citizen citizen) {
		this.citizen = citizen;
	}

	public WorkBuilder at(String company)
	{
		this.citizen.company = company;
		return this;
	}
	
	public WorkBuilder as(String position)
	{
		this.citizen.position = position;
		return this;
	}
	
	public WorkBuilder earns(String salary)
	{
		this.citizen.salary = salary;
		return this;
	}
}

public class FacetedBuilder
{
	public static void main(String[] args) {
		
		CitizenBuilder cb = new CitizenBuilder("gg");
		cb.lives().in("511 Periyar Nagar")
		          .at("626203")
		          .worksAt()
		          .as("engineer")
		          .at("paytm")
		          .earns("45000/month");
		
		System.out.println(cb.build());
	}
}

//Output: Citizen [name=gg, streetName=511 Periyar Nagar, zipCode=626203, company=null, position=null, salary=null]

//First chain is ok because we pass the citizen object Ref into the new derived object for which a new base class is created 
//through super.
//So during second chain, this new base class which has a new citizen ref(empty values)
//is passed to 2nd derived class, hence ambiguity in output.



