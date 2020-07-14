package design.pattern.creational.factory;

//Makes much more descriptive and easy to maintain code else creation logic becomes tedious.
public class Point {
	
	private double x;
	private double y;
	
	//Forcing user to use factory methods for creating objects
	private Point(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	//Move all factory methods to a factory
	//MAIN : Factory methods are STATIC because they instantiate classes. Hence should be usable without instance.
	
	//static inner-classes can have non-static methods. 
	//But static methods must be only in static inner-classes.
	
	public static class Factory{
		
		public static Point newCartesianPoint(double x, double y)
		{
			return new Point(x, y); 
		}
		
		public static Point newPolarPoint(double x, double y)
		{
			return new Point(x * Math.cos(y), x * Math.sin(y));
		}
	
	}
}

//If factory is separate class, then construtor has to be public. IF no problem follow this.
//If having access to object, then create factory inside the object itself.

class Demo{
	
	public static void main(String[] args) {
		
		Point p = Point.Factory.newPolarPoint(5, 6);
		
	}
}