package design.pattern.structural.bridge;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

/*We have a class shape, which can be inherited.
We can render the shape using vector rendering or raster rendering.
Without bridge pattern, say there are 3 shapes - square, circle, rectangle.
							and two renderers  - vector, raster.
Each shape is to have two renders. then 3 * 2 => 6 implementation classes.

SquareVector, SquareRaster .....etc (3 * 2 = 6). This is cartesian complexity explosion. 
With increasing shapes and renders complexity increases.

Introduce Bridge Design Pattern. We have separate interface for renderer and
have a renderer reference inside the abstract class. We can pass the renderer at runtime.

Dependency injection with google-guice.
*/

abstract class Shape {
	public Renderer renderer; //Bridge between Shape and Renderer abstractions. More loosely coupled.
	abstract public void drawShape();
}

interface Renderer
{
	void drawCircle();
	void drawRectangle();
	void drawTriangle();
}

class VectorRenderer implements Renderer
{

	@Override
	public void drawCircle() {
		System.out.println("drawing circle with lines");
	}

	@Override
	public void drawRectangle() {
		System.out.println("drawing rectangle with lines");
	}

	@Override
	public void drawTriangle() {
		System.out.println("drawing triangle with lines");
	}
	
}

class RasterRenderer implements Renderer
{

	@Override
	public void drawCircle() {
		System.out.println("drawing circle with pixels");
	}

	@Override
	public void drawRectangle() {
		System.out.println("drawing rectangle with pixels");
	}

	@Override
	public void drawTriangle() {
		System.out.println("drawing triangle with pixels");
	}
	
}

class Circle extends Shape
{
	@Inject //Injecting the renderer through guice. When we need same renderer for all our shapes.
	Circle(Renderer renderer)
	{
		this.renderer = renderer;
	}

	@Override
	public void drawShape() {
		renderer.drawCircle();
	}
}

class Rectangle extends Shape
{
	@Inject
	Rectangle(Renderer renderer)
	{
		this.renderer = renderer;
	}

	@Override
	public void drawShape() {
		renderer.drawRectangle();
	}
}

class Triangle extends Shape
{
	@Inject
	Triangle(Renderer renderer)
	{
		this.renderer = renderer;
	}

	@Override
	public void drawShape() {
		renderer.drawTriangle();
	}
}

//configuring the dependency
class ShapeModule extends AbstractModule
{

	@Override
	protected void configure() {
		bind(Renderer.class).to(RasterRenderer.class); //Injecting vector-renderer for all shapes.
	}
	
}

public class BridgeDemo
{
	public static void main(String[] args) {
		
		/*
		 * Here, we have to input the renderer in every contructor.
		 * When more classes are present this is a tedious job. 
		 * We can have a dependency injection framework like google-guice to inject the required renderer 
		 * to our shape classes.
		 */
//		Shape circle    = new Circle(new VectorRenderer());
//		Shape rectangle = new Rectangle(new RasterRenderer());
//		Shape triangle  = new Triangle(new VectorRenderer());
//		
//		circle.drawShape();
//		rectangle.drawShape();
//		triangle.drawShape();
		
		//Using google-guice
		//Dependencies - javax.inject jar, aopalliance.jar
		Injector injector = Guice.createInjector(new ShapeModule());
		
		Circle instance = injector.getInstance(Circle.class);
		Rectangle instance2 = injector.getInstance(Rectangle.class);
		Triangle instance3 = injector.getInstance(Triangle.class);
		
		instance.drawShape();
		instance2.drawShape();
		instance3.drawShape();
	}
}










