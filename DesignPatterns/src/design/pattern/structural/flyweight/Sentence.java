package design.pattern.structural.flyweight;

import java.util.HashMap;
import java.util.Map;

class Sentence
{
  //Flyweight useful in applications like text editors where we encounter same words repeatedly in different places in a document.
  private static Map<Integer, WordToken> words = new HashMap<>(); //Using map to implement flyweight
  
  private int[] wordPointers; //Makes note of the order of words in a sentence and the pointer integer is the 'key' to the map.
  
  public Sentence(String plainText)
  {
    String[] tokens = plainText.split(" ");
    wordPointers = new int[tokens.length];
    
    for(int i = 0 ; i < tokens.length ; i++)
        wordPointers[i] = getIndex(tokens[i]);
  }
  
  private int getIndex(String token)
  {
	  //Flyweight pattern - If word already present just returning index. Not creating a new word.
      for(Integer index: words.keySet())
      {
          if(words.get(index).word.equals(token))
            return index;
      }
      
      int size = (words.size() == 0) ? -1 : words.size() - 1;
      words.put(size+1, new WordToken(token));
      
      return size+1;
  }
  
  public WordToken getWord(int index)
  {
    return words.get(index);
  }

  @Override
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    for(int i = 0 ; i < wordPointers.length; i++)
    {
        WordToken token = words.get(wordPointers[i]);
        if(token.capitalize)
            token.word = token.word.toUpperCase();
        
        sb.append(token.word + " ");
    }
    
    return sb.toString().trim();
  }

  class WordToken
  {
    public boolean capitalize;
    public String word;
    
    WordToken(String word)
    {
        this.word = word;
    }
  }
}