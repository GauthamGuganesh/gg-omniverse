package design.pattern.behavioural.visitor;

//Have a visitor entrypoint(accept method) in at every level in hierarchy. Create visitor instance.
//Feed the hierarchy object to the visitor instance. Let it visit every level recursively.

interface VisitorIntf{} //Acyclic Visitor - We can choose not to visit some classes if we want.

interface DoubleExpressionVisitor extends VisitorIntf //Separate interfaces.
{
	void visit(DoubleExpression e);
}

interface AdditionExpressionVisitor extends VisitorIntf //Segregate interfaces --> Making it acyclic. 
{
	void visit(AdditionExpression e);
}

abstract class Expression
{
	public abstract void print(StringBuilder sb); //Printing at all levels in hierarchy. But Intrusive Visitor.
	
	//Having Visitor marker interface type because we may choose to not visit a particular class.
	public abstract void accept(VisitorIntf visitor); 
	//Implementing ClassicVisitor. Any change can be done in the implementations of ExpressionVisitor. Not too intrusive, but covers hierarchy also.
}

class DoubleExpression extends Expression
{
	double value;
	
	public DoubleExpression(double value) {
		this.value = value;
	}

	@Override
	public void print(StringBuilder sb) {
		sb.append(value);
	}

	@Override
	public void accept(VisitorIntf visitor) {
		if(visitor instanceof DoubleExpressionVisitor)
			((DoubleExpressionVisitor) visitor).visit(this);
	}
	
}

class AdditionExpression extends Expression
{
	Expression left;
	Expression right;
	
	AdditionExpression(Expression left, Expression right)
	{
		this.left = left;
		this.right = right;
	}

	@Override
	public void print(StringBuilder sb) { //Recursive printing
		sb.append("(");
		left.print(sb);
		sb.append("+");
		right.print(sb);
		sb.append(")");
	}

	@Override
	public void accept(VisitorIntf visitor) {
		if(visitor instanceof AdditionExpressionVisitor)
			((AdditionExpressionVisitor) visitor).visit(this);; //That's all required here. All visitor logic moved to implementation
	}
	
}

//We can choose to implement any one visitor thereby leaving out visiting some levels.
class PrintVisitor implements DoubleExpressionVisitor, AdditionExpressionVisitor //Classic Visistor
{
	StringBuilder sb = new StringBuilder();
	
	@Override
	public void visit(DoubleExpression e) {
		sb.append(e.value);
	}

	@Override
	public void visit(AdditionExpression e) {
		sb.append("(");
		e.left.accept(this);
		sb.append("+");
		e.right.accept(this);
		sb.append(")");
	}
	
	@Override
	public String toString()
	{
		return sb.toString();
	}
	
}

//class EvaluationVisitor implements ExpressionVisitor //Evaluating the expression
//{
//	double result;
//
//	@Override
//	public void visit(DoubleExpression e) {
//		result = e.value;
//	}
//
//	@Override
//	public void visit(AdditionExpression e) {
//		e.left.accept(this);
//		double left = result;
//		e.right.accept(this);
//		double right = result;
//		
//		result = left + right;
//	}
//	
//	@Override
//	public String toString()
//	{
//		return Double.toString(result);
//	}
//}

class ReflectivePrinter //Reflective Visitor - Using reflection. Isolating printing function in a separate class.
{
	public static void print(Expression e, StringBuilder sb)
	{
		if(e.getClass() == DoubleExpression.class)
			sb.append(((DoubleExpression) e).value);
		else if(e.getClass() == AdditionExpression.class)
		{
			sb.append("(");
			print(((AdditionExpression)e).left, sb);
			sb.append("+");
			print(((AdditionExpression)e).right, sb);
			sb.append(")");
		}
	}
}

public class Visitor {
	
	public static void main(String[] args) {
		AdditionExpression addition = new AdditionExpression(new DoubleExpression(4), 
				              new AdditionExpression(new DoubleExpression(3), 
				              new AdditionExpression(new DoubleExpression(6), new DoubleExpression(8))));
		
		StringBuilder sb1 = new StringBuilder();
		addition.print(sb1); //Intrusive
		System.out.println(sb1.toString());
		
		StringBuilder sb2 = new StringBuilder();
		ReflectivePrinter.print(addition, sb2); //Reflective
		System.out.println(sb2.toString());
		
		PrintVisitor ep = new PrintVisitor();
		ep.visit(addition);
		
		System.out.println(ep); //Classic Visitor - double dispatch
		
//		EvaluationVisitor ev = new EvaluationVisitor(); //Classic Visitor evaluation
//		ev.visit(addition);
//		System.out.println(ep + " = " + ev.result);
	}

}
