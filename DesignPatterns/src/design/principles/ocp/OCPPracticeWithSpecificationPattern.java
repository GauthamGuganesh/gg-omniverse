package design.principles.ocp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class OCPPracticeWithSpecificationPattern 
{
	public static void main(String[] args)
	{
		Product tree = new Product("Tree", Color.GREEN, Size.LARGE);
		Product plant = new Product("Plant", Color.BLUE, Size.MEDIUM);
		Product sapling = new Product("Sapling", Color.GREEN, Size.SMALL);
		
		List<Product> productList = new ArrayList<Product>();
		productList.add(tree);
		productList.add(plant);
		productList.add(sapling);
		
		BetterProductFilter bf = new BetterProductFilter();
		bf.filter(productList, new ColorSpecification(Color.BLUE)).forEach(p -> System.out.println(p.name + " " + p.color));
		bf.filter(productList, new SizeSpecification(Size.MEDIUM)).forEach(p -> System.out.println(p.name + " " + p.size));
		bf.filter(productList, new ColorSpecification(Color.GREEN)).forEach(p -> System.out.println(p.name + " " + p.color));
	}
}

interface Filter<T> 
{
	public Stream<T> filter(List<T> items, Specification<T> spec); //Applies the specified criterias on the list of items and filters them.
}

interface Specification<T> // A specification checks for a criteria on an item to be true or not. Criteria  given inside method isTrue.
{
	public boolean isTrue(T item);
}

enum Color
{
	RED,
	BLUE,
	GREEN
}

enum Size
{
	SMALL,
	MEDIUM,
	LARGE
}

class Product
{
	public String name;
	public Color color;
	public Size size;
	
	public Product(String name, Color color, Size size) 
	{
		this.name = name;
		this.color = color;
		this.size = size;
	}	
	
	/*If we add filters inside this class, as the filter conditions increase, we would have to keep adding code inside this class.
	 also it violates single-responsibility-principle since product having additional responsibility of filtering. */
}

class ProductFilter
{
	/*Adding filter conditions here will make it grow once application is scaled, will be difficult to maintain. Prone
	 * to other errors while modifications. Should be closed to modifications.
	 */
}

class BetterProductFilter implements Filter<Product> //Open to extension, implementation.
{

	@Override
	public Stream<Product> filter(List<Product> items, Specification<Product> spec)
	{
		return items.stream().filter(product -> spec.isTrue(product));
	}
	
}

class ColorSpecification implements Specification<Product> //Can include many specifications we want without modifying code. Size, color, price etc.
{
	private Color color;
	
	public ColorSpecification(Color color)
	{
		this.color = color;
	}

	@Override
	public boolean isTrue(Product item) 
	{
		if(item.color == color) return true;
		else return false;
	}
	
}

class SizeSpecification implements Specification<Product> //Can include many specifications we want without modifying code. Size, color, price etc.
{
	private Size size;
	
	public SizeSpecification(Size size)
	{
		this.size = size;
	}

	@Override
	public boolean isTrue(Product item) 
	{
		if(item.size == size) return true;
		else return false;
	}
	
}
