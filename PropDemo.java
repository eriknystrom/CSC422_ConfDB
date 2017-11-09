import java.util.*;

public class PropDemo {
   public static void main(String args[]) {
      Properties capitals = new Properties();
      Set states;
      String str;
      
      capitals.put("Illinois", "Springfield");
      capitals.put("Missouri", "Jefferson City");
      capitals.put("Washington", "Olympia");
      capitals.put("California", "Sacramento");
      capitals.put("Indiana", "Indianapolis");

      // Show all states and capitals in hashtable.
      states = capitals.keySet(); // get set-view of keys
      Iterator itr = states.iterator();
      while(itr.hasNext()) {
         str = (String) itr.next();
         System.out.println("The capital of " +
            str + " is " + capitals.getProperty(str) + ".");
      }
      System.out.println();

      // look for state not in list -- specify default
      str = capitals.getProperty("Florida", "Not Found");
      System.out.println("The capital of Florida is "
          + str + ".");

      System.out.println("Illinois  : "+capitals.getProperty("Illinois"));
      System.out.println("Missouri  : "+capitals.getProperty("Missouri"));
      System.out.println("Washington: "+capitals.getProperty("Washington"));
      System.out.println("California: "+capitals.getProperty("California"));
      System.out.println("Indiana   : "+capitals.getProperty("Indiana"));




   }
}
