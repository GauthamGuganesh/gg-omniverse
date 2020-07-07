package Builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Buidling custom html builder
class HtmlElement
{
	private String name;
	private String text;
	private int indentSpace = 2; //Default level of indentation. Will increase with depth.
	private String newLine = System.lineSeparator();
	
	//Each element may also have child elements. 
	private List<HtmlElement> children = new ArrayList<>();
	
	public HtmlElement(String name, String text)
	{
		this.name = name;
		this.text = text;
	}
	
	public String toStringImpl(int indentLevel)
	{
		StringBuilder sb = new StringBuilder();
		
		//Joining a list of elements into string using String.join(delimiter, Collection that has string elements);
		//Collection.ncopies(int n, object) => makes a immutable list of 'n' copies of the object.
		
		String indent = String.join("", Collections.nCopies(indentLevel * indentSpace, " "));
		
		//String.format => (format, ... args- any number of args corresponding to format)
		//format %s => for string
		
		sb.append(String.format("%s<%s>%s", indent, name, newLine));
		
		if(text != null && !text.isEmpty())
		{
			String textIndnt = String.join("", Collections.nCopies(indentLevel + 2, " "));
			sb.append(String.format("%s%s%s", textIndnt, text, newLine));
		}
		
		for(HtmlElement e : children)
		{
			sb.append(e.toStringImpl(indentLevel + 1)); //Appending child elements recursively.
		}
		
		sb.append(String.format("%s</%s>%s", indent, name, newLine));
		return sb.toString();
	}
	
	public void addChildren(HtmlElement e)
	{
		children.add(e);
	}
	
	@Override
	public String toString() {
		
		return toStringImpl(0);
	}
}

//Dedicated builder to build html elements
//(For simplicity purpose, we build one level of child only)
class HtmlBuilder
{
	//Every html has a root element. We store it here and add children as and when user needs.
	private HtmlElement root;
	
	public HtmlBuilder(String rootElement)
	{
		root = new HtmlElement(rootElement, "");
	}
	
	public HtmlBuilder appendChild(String childName, String childText)
	{
		HtmlElement e = new HtmlElement(childName, childText);
		root.addChildren(e);
		return this;
	}
	
	@Override
	public String toString() {

		return root.toString();
	}
}

public class Demo {
	
	public static void main(String[] args) 
	{
		StringBuilder s = new StringBuilder();
		
		//This is an example of fluent builder. Building each element can be chained.
		s.append("Hello").append("World");
		
		System.out.println("<p> Paragraph Element </p>");
		
		//You need to output a unordered list. 
     	//Using string concatenation(+) will be complex and error prone.
     	//We can use inbuilt String Builder.
     	
		String[] words = { "Hello", "World" };
		StringBuilder sb = new StringBuilder();
		sb.append("<ul>\n");
		for(String word: words)
		{
			sb.append("<li>" + word + "</li>\n");
		}
		sb.append("</ul>");
		System.out.println(sb);   
		
		System.out.println("\n\n\n\n");
		
		//This is an example of piece-by-piece object building using an in-built java builder like StringBuilder.
		
		//Fluent builer.
		HtmlBuilder hb = new HtmlBuilder("ul");
		hb.appendChild("li", "Hello")
		  .appendChild("li", "World"); //Chaining
		
		System.out.println(hb.toString());
	}

}
