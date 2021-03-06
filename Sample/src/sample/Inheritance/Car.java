package sample.Inheritance;

class Fortuner extends Car
{
	Fortuner(int cylinders, String name)
	{
		super(cylinders, name);
	}
	
	@Override
	public void startEngine() 
	{
		System.out.println("Fortuner.startEngine called. Starting " + getName());
	}
	
	@Override
	public void accelerate(int speed) 
	{
		System.out.println("Fortuner.accelerate called. 4 wheel acceleration. Current Speed : " + getSpeed());
		int fortunerSpeed = getSpeed();
		fortunerSpeed = 2 * (fortunerSpeed + speed);
		setSpeed(fortunerSpeed);
		System.out.println("Acceleration twice the amount. Speed now at : " + getSpeed());
	}
}

class TataInnova extends Car
{
	TataInnova(int cylinders, String name)
	{
		super(cylinders, name);
	}
	
	@Override
	public void startEngine() 
	{
		System.out.println("TataInnova.startEngine called. Starting " + getName());
	}
}

class Alto extends Car
{
	Alto(int cylinders, String name)
	{
		super(cylinders, name);
	}
	
	@Override
	public void startEngine() 
	{
		System.out.println("Alto.startEngine called. Starting " + getName());
	}
}

public class Car
{
	private int cylinders;
	private int wheels;
	private int speed;
	private boolean engine;
	private String name;
	
	public Car(int cylinders, String name)
	{
		this(cylinders, name, 4, 0, true);
	}
	
	private Car(int cylinders, String name, int wheels, int speed, boolean engine)
	{
		this.cylinders = cylinders;
		this.wheels = wheels;
		this.name = name;
		this.engine = engine;
		this.speed = 0;
	}

	public void startEngine()
	{
		System.out.println("Car.StartEngine called. Starting " + getName());
	}
	
	public void accelerate(int speed)
	{
		System.out.println("Car.accelerate called. Accelerating...");
		this.speed += speed;
		System.out.println("Accelerated speed to : " + this.speed);
	}
	
	public void brake()
	{
		System.out.println("Car.brake called. Braking...");
		this.speed = 0;
		System.out.println("Braked. Speed : " + this.speed);
	}
	
	public int getCylinders() {
		return cylinders;
	}

	public void setCylinders(int cylinders) {
		this.cylinders = cylinders;
	}

	public int getWheels() {
		return wheels;
	}

	public void setWheels(int wheels) {
		this.wheels = wheels;
	}

	public boolean isEngine() {
		return engine;
	}

	public void setEngine(boolean engine) {
		this.engine = engine;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public static void main(String[] args)
	{
		for(int i = 0 ; i < 4 ; i++)
		{
			Car car = getCar(i);
			car.startEngine();
			car.accelerate(10);
			car.brake();
		}
		
		Car inlineCar = new Alto(4, "Gautham's inline Alto") 
		{
			@Override
			public void startEngine() 
			{
				System.out.println("Inline Alto class.");
			}
		};
		
		inlineCar.startEngine();
		inlineCar.brake();
	}
	
	public static Car getCar(int randomVariable)
	{
		switch(randomVariable)
		{
		case 0:
			return new Car(4, "Gautham's Car");
		case 1:
			return new Fortuner(4, "Gautham's Fortuner");
		case 2:
			return new TataInnova(4, "Gautham's Innova");
		case 3:
			return new Alto(4, "Gautham's Alto");
		default:
				return null;
		}
	}
}