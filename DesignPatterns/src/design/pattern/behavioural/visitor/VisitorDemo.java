package design.pattern.behavioural.visitor;

abstract class ExpressionVisitor
{
  abstract void visit(Value value);
  abstract void visit(AdditionExpresion ae);
  abstract void visit(MultiplicationExpression me);
}

abstract class Expresion
{
  abstract void accept(ExpressionVisitor ev);
}

class Value extends Expresion
{
  public int value;

  public Value(int value)
  {
    this.value = value;
  }

  @Override
  void accept(ExpressionVisitor ev)
  {
    ev.visit(this);
  }
}

class AdditionExpresion extends Expresion
{
  public Expresion lhs, rhs;

  public AdditionExpresion(Expresion lhs, Expresion rhs)
  {
    this.lhs = lhs;
    this.rhs = rhs;
  }

  @Override
  void accept(ExpressionVisitor ev)
  {
    ev.visit(this);
  }
}

class MultiplicationExpression extends Expresion
{
  public Expresion lhs, rhs;

  public MultiplicationExpression(Expresion lhs, Expresion rhs)
  {
    this.lhs = lhs;
    this.rhs = rhs;
  }

  @Override
  void accept(ExpressionVisitor ev)
  {
    ev.visit(this);
  }
}

class ExpressionPrinter extends ExpressionVisitor
{
  private StringBuilder sb = new StringBuilder();

  @Override
  public void visit(Value value)
  {
      sb.append(value.value);
  }
  
  public void visit(AdditionExpresion ae)
  {
    sb.append("(");
    ae.lhs.accept(this);
    sb.append("+");
    ae.rhs.accept(this);
    sb.append(")");
  }
  
  public void visit(MultiplicationExpression me)
  {
    me.lhs.accept(this);
    sb.append("*");
    me.rhs.accept(this);
  }

  @Override
  public String toString()
  {
    return sb.toString();
  }
}

public class VisitorDemo
{
	public static void main(String[] args) {
		AdditionExpresion additionExpresion = new AdditionExpresion(new Value(5), new AdditionExpresion(new Value(4), 
											new MultiplicationExpression(new Value(2), new Value(1))));
		ExpressionPrinter ep = new ExpressionPrinter();
		ep.visit(additionExpresion);
		System.out.println(ep.toString());
	}
	
	
	
	
	
	
	
	
	
}