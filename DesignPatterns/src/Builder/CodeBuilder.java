package Builder;

//Builder exercise;

import java.util.Map;
import java.util.HashMap;

class CodeBuilder
{
    private String className;
    //Fieldname to FieldType map
    private Map<String, String> fields = new HashMap<>();
    
    public CodeBuilder(String className)
    {
        this.className = className;
    }
    
    public CodeBuilder addField(String name, String type)
    {
        fields.put(name, type);
        return this;
    }
    
    @Override
    public String toString() 
    {
        StringBuilder sb = new StringBuilder();
        sb.append("public class "+className+"\n")
          .append("{\n");
          
        for(String field: fields.keySet())
        {
          sb.append("  public ")
            .append(fields.get(field))
            .append(" ")
            .append(field)
            .append(";\n");
         }
      sb.append("}");
      
      return sb.toString();
    }
    
    public static void main(String[] args) {
		
    	CodeBuilder cb = new CodeBuilder("Person");
    	cb.addField("name", "String")
    	  .addField("address", "String")
    	  .addField("Phone", "int");
    	
    	System.out.println(cb.toString());
	}
}
