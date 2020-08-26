package design.pattern.structural.decorator;

public class DynamicDecorator 
{
	public static void main(String[] args) {
		
		ColoredShape blueSquare = new ColoredShape(new Square(), "blue");
		System.out.println(blueSquare.getInfo());
		
		TransparentShape transparentShape = new TransparentShape(new Circle(), 20);
		System.out.println(transparentShape.getInfo());
		
		//Double layer decoration
		TransparentShape blueTransparentSquare = new TransparentShape(blueSquare, 50);
		System.out.println(blueTransparentSquare.getInfo());
		
		BorderedShape<TransparentShape> borderedShape = new BorderedShape<>(blueTransparentSquare, 3);
		System.out.println(borderedShape.getInfo());
	}
}

interface Shape
{
	String getInfo();
}

class Square implements Shape
{

	@Override
	public String getInfo() {
		return "This is a square";
	}
	
}

class Circle implements Shape
{

	@Override
	public String getInfo() {
		return "This is a circle";
	}
	
}

//Dynamic decorator. Type of shape is determined at runtime.
class ColoredShape implements Shape //Decorator and decorating-object of same super type. Multiple level of wrappings easily done.
{
	Shape shape; //Decorator pattern. Adding functionality color.
	String color;
	
	ColoredShape(Shape shape, String color)
	{
		this.shape = shape;
		this.color = color;
	}

	@Override
	public String getInfo() {
		return shape.getInfo() + " of color " + color;
	}
}

class TransparentShape implements Shape 
{
	Shape shape;
	int transparency;
	
	public TransparentShape(Shape shape, int transparency) {
		this.shape = shape;
		this.transparency = transparency;				
	}

	@Override
	public String getInfo() {
		return shape.getInfo() + " of transaprency " + transparency + "%";
	}
	
	
}

//Static Decorator. Type of shape decided at compile time. Reduces runtime overhead.
class BorderedShape<T extends Shape> implements Shape
{
	Shape shape;
	int border;
	
	public BorderedShape(T shape, int border) {
		this.shape = shape;
		this.border = border;
	}

	@Override
	public String getInfo() {
		return shape.getInfo() + " ,with border = " + border + "mm";
	}
}








