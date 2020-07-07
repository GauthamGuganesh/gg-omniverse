package Builder;

class Sample
{
	
}

class A
{
	protected Sample sample = new Sample();
	
	A()
	{
		System.out.println("From Base class " + this.hashCode());
	}
	
	void print()
	{
		System.out.println(sample);
	}
	
	void doAB()
	{
		new B().printmess(sample);;
		new C().printmess(sample);;
	}
}

class B extends A
{ 
	B(){}
	
	B(Sample sample)
	{
		super();
	}
	
	@Override
	void print()
	{
		System.out.println(sample);
	}
	
	void printmess(Sample sample)
	{
		System.out.println(sample);
	}
}

class C extends A
{
	C(){}
	C(Sample sample)
	{
		super();
	}
	
	@Override
	void print()
	{
		System.out.println(sample);
	}
	
	void printmess(Sample sample)
	{
		System.out.println(sample);
	}
}

public class sampleScribble {
	
	public static void main(String[] args) {
		
		new A().print();
		new B().print();
		new C().print();
		
		
		System.out.println(new B().hashCode());
		System.out.println(new C().hashCode());
		
		new A().doAB(); // pass the reference to keep integrity of object ref.
		
	}

}

/* From Base class 1028566121
1028566121
From Base class 1118140819
1118140819
Hash codes of base class and derived. Evidence that each derived class has its own base class */ 

