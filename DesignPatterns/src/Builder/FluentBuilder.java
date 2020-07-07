package Builder;

class Person
{
	private String name;
	private String work;
	
	protected Person(String name, String work)
	{
		this.name = name;
		this.work = work;
	}
	
	@Override
	public String toString() {
		
		return name + " " + work;
	}
}

//RECURSIVE GENERICS. Create builder object with derived class and then use the methods,(then only SELF will receive an argument)
                   // since derived class has maximum functionality(base func + own func). 

//Builder has to be descriptive in its methods.

//Accepting a type argument of SELF => which can be any class that extends PersonBuilder.

class PersonBuilder<SELF extends PersonBuilder<SELF>>  
{
	private String name;
	protected String work;
	
	public SELF withName(String name) 
	{
		this.name = name;
		return self();
	}
	
	protected SELF self()
	{
		//Made to return any builder object that is a derived class of this one.
	    //Maybe employee builder, address builder, zipcode builder etc.
		
		return (SELF) this; //If no generics, will return base class when called from derived class, hence cant be fluent.
	}
	
	public Person build()
	{
		return new Person(name, work);
	}
}

class EmployeeBuilder extends PersonBuilder<EmployeeBuilder>
{	
	public EmployeeBuilder worksAt(String worktype)
	{
		work = worktype;
		return self();
	}
	
	//Base class methods always going to be accessible from this instance, so when returning 'this' we will be able to access
	// all base class methods.   
	
	@Override
	protected EmployeeBuilder self() 
	{
		return this;
	}
}

//Fluent builder under inheritance
public class FluentBuilder 
{
	public static void main(String[] args) 
	{
		EmployeeBuilder eb = new EmployeeBuilder();
		
		eb.worksAt("Software").withName("gg");
		System.out.println(eb.build());
		
	}
}







