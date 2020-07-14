package design.pattern.creational.singleton;

enum EnumBasedSingleton{
	
	/* internally above enum Color is converted to
	class Color
	{
	     public static final Color RED = new Color();
	     public static final Color BLUE = new Color();
	     public static final Color GREEN = new Color(); object creation implicit hence cannot explicitly use enum constructors.
	}*/
	
	INSTANCE; // Enum based singleton. Thread-safe but problem with serializing.
	
	EnumBasedSingleton()
	{
		this.value = 42;
	}
	
	private int value;

	public int getValue() {		
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public static void main(String[] args) {
		
		EnumBasedSingleton obj = EnumBasedSingleton.INSTANCE;
		obj.setValue(5);
		System.out.println(obj.getValue());
		
	}
}
