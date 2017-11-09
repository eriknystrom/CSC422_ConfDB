

/**
 * Write a description of class PropertiesDemo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
public class PropertiesDemo
{
    public static void main(String[] args)
    {
        /*
         * Data Strructure to handle a relational table in Java
         * Vector of Properties
         * PERSON TABLE
         * ID   Name    Age     Gender
         * ----------------------------
         * 001  Bob     21      M
         * 002  Tom     22      M
         * 003  Jen     20      F
         * ---------------------------
         */
        Properties row1 = new Properties();
        row1.put("Id", "001");
        row1.put("Name", "Bob");
        row1.put("Age", "21");
        row1.put("Gender", "M");
        
        Properties row2 = new Properties();
        row2.put("Id", "002");
        row2.put("Name", "Tom");
        row2.put("Age", "22");
        row2.put("Gender", "M");
        
        Properties row3 = new Properties();
        row3.put("Id", "003");
        row3.put("Name", "Jen");
        row3.put("Age", "20");
        row3.put("Gender", "F");
        
        printPairs(row1);
        printPairs(row2);
        printPairs(row3);
        
  
        
        Vector<Properties> PERSON = new Vector<Properties>();
        PERSON.add(row1);
        PERSON.add(row2);
        PERSON.add(row3);
        
        printValues(PERSON);
    }
    public static void printPairs(Properties p)
    {
        System.out.println("(Id, "+p.getProperty("Id")+")");
        System.out.println("(Name, "+p.getProperty("Name")+")");
        System.out.println("(Age, "+p.getProperty("Age")+")");
        System.out.println("(Gender, "+p.getProperty("Gender")+")");
    }
    
    public static void printValues(Vector<Properties> v)
    {
        for (Properties p : v)
        {
            Enumeration props = p.propertyNames();
            while (props.hasMoreElements())
                System.out.print(p.getProperty((String)(props.nextElement()))+"\t");
            System.out.println();
        }
    }
    
}
