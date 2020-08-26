package design.pattern.behavioural.memento;

import java.util.ArrayList;
import java.util.List;

class Token
{
  public int value = 0;

  public Token(int value)
  {
    this.value = value;
  }
}

class TokenMemento
{
  public List<Token> tokens = new ArrayList<>();
}

class TokenMachine
{
  public List<Token> tokens = new ArrayList<>();

  public TokenMemento addToken(int value)
  {
    tokens.add(new Token(value));
    
    TokenMemento m = new TokenMemento();
    for(Token token : tokens)
        m.tokens.add(token);
        
    return m;
  }

  public TokenMemento addToken(Token token)
  {
    tokens.add(new Token(token.value)); //Argument reference Token might change in future. So do a deep copy.
    
    TokenMemento m = new TokenMemento();
    for(Token t : tokens)
        m.tokens.add(t);
        
    return m;
  }

  public void revert(TokenMemento m)
  {
    tokens = new ArrayList<>(); //Don't give direct reference to memento state. deep copy since memento has to be read-only.
    for(Token t : m.tokens)
        tokens.add(t);
  }
}