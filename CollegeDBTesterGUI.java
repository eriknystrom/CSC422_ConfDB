import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.*;

/**
 * For CSC/CIS 422 class
 * 
 * @author (your name) 
 * @version (2017)
 */
 //----------------------------------------------------------
public class CollegeDBTesterGUI extends Application
{   
    // Declare all the GUI components as instance variables. 
    // Don't create the objects at this point.
    // First we need a GridPane
    private GridPane   pane;
    
    // We need 5 labels
    private Label      displayLabel;
    private Label      q1Label;
    private Label      q2Label;
    private Label      q3Label;
    private Label      q4Label;
    private Label      q5Label;
    private Label      q6Label;
    private Label      q7Label;
    private Label      q8Label;
    
    //We need 5 text fields
    private TextField status2TF;
    private TextField deptId3TF;
    private TextField deptId4TF;
    private TextField bannerId5TF;
    private TextField deptId6TF;
    private TextField deptName6TF;
    private TextField studentId7TF;
    private TextField status7TF;
    private TextField deptId8TF;
    //private TextArea textArea;
    
    // We need a button
    private Button q1;
    private Button q2;
    private Button q3;
    private Button q4;
    private Button q5;
    private Button q6;
    private Button q7;
    private Button q8;
    private Button quit;
    
    private TextArea results;
    
    // We need a scene
    private Scene scene;
    
    //----------------------------------------------------------
    // crtate GUI components
    private void createGUIComponents()
    {
        //Create GUI Objects
        pane = new GridPane();
        
        //----------------------------------------------------------
        //Create Labels
        displayLabel = new Label("DISPLAY INFORMATION");
        q1Label = new Label("Q1. All the information from Professor table");
        q2Label = new Label("Q2. BannerId and Names of all students with this ");
        q3Label = new Label("Q3. Professor id, name, and Department for this ");
        q4Label = new Label("Q4. Prof Name, Crs Name, Sem and Section for all courses in this ");
        q5Label = new Label("Q5. Stu name, Crs Name, Sem, Section & grade for the student with this  ");
        q6Label = new Label("Q6. Insert a new department ");
        q7Label = new Label("Q7. Update a Student Status ");
        q8Label = new Label("Q8. Delete a department ");
        
        //----------------------------------------------------------
        // Create the text fields
        status2TF = new TextField("<status>");
        deptId3TF = new TextField("<deptId>");
        deptId4TF = new TextField("<deptId>");
        bannerId5TF = new TextField("<bannerId>");
        deptId6TF = new TextField("<deptId>");
        deptName6TF = new TextField("<dept name>");
        studentId7TF = new TextField("<studentId>");
        status7TF = new TextField("<new status>");
        deptId8TF = new TextField("<deptId>");
        
        //----------------------------------------------------------
        // Create the buttons
        q1 = new Button("Q1");
        q2 = new Button("Q2");
        q3 = new Button("Q3");
        q4 = new Button("Q4");
        q5 = new Button("Q5");
        q6 = new Button("Q6");
        q7 = new Button("Q7");
        q8 = new Button("Q8");
        quit = new Button("Quit");
        
        //----------------------------------------------------------
        // Create the text area to display results
        results = new TextArea();
 
        
        //----------------------------------------------------------
        //Add the components to the pane
        pane.add(displayLabel, 0, 0); 
        pane.add(q1Label,      0, 1);  pane.add(q1,  3,  1);
        pane.add(q2Label,      0, 2);  pane.add(status2TF, 1, 2);     pane.add(q2,  3,  2);
        pane.add(q3Label,      0, 3);  pane.add(deptId3TF, 1, 3);     pane.add(q3,  3,  3);
        pane.add(q4Label,      0, 4);  pane.add(deptId4TF, 1, 4);     pane.add(q4,  3,  4);
        pane.add(q5Label,      0, 5);  pane.add(bannerId5TF, 1, 5);   pane.add(q5,  3,  5);
        pane.add(q6Label,      0, 6);  pane.add(deptId6TF, 1, 6);     pane.add(deptName6TF, 2, 6); pane.add(q6,  3,  6);
        pane.add(q7Label,      0, 7);  pane.add(studentId7TF, 1, 7);  pane.add(status7TF, 2, 7); pane.add(q7,  3,  7);
        pane.add(q8Label,      0, 8);  pane.add(deptId8TF, 1, 8);     pane.add(q8,  3,  8);
                                                                      pane.add(quit,3, 9); 
        pane.add(results,      0, 9);
     
        
        // Set Properties
        pane.setAlignment(Pos.CENTER);
        
    }
    //----------------------------------------------------------
    //Attach handlers
    public void attachHandlers()
    {
	//----------------------------------------------------------
        
        q1.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
               	String query = "SELECT * FROM  PROFESSOR_15 "; 
               	System.out.println(query);
               	String displayString = retrieveFromTable(query);
               	results.setText(displayString);
            }
        }
        );  
	//----------------------------------------------------------
     	
        q2.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
                
            	String status = status2TF.getText();
            	String query = "SELECT BannerId, StudentName FROM STUDENT_15 WHERE Status = '"+
                    status + "';"; 
            	String displayString = retrieveFromTable(query);
            	results.setText(displayString);
            }
        }
        );  
	//----------------------------------------------------------
     	
        q3.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {  
            	String deptId = deptId3TF.getText();
            	String query = "SELECT P.ProfessorId, P.ProfessorName, "+
            			"D.DepartmentName FROM PROFESSOR_15 P, DEPARTMENT_15 D "+ 
            			"WHERE P.DepartmentId = D.DepartmentId AND P.DepartmentId = '" + 
            			deptId +"';"; 
            	String displayString = retrieveFromTable(query);
            	results.setText(displayString);
            }
        }
        ); 
	//---------------------------------------------------------- 
     	
        q4.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
               
            	String deptId = deptId4TF.getText();
            	String query = "SELECT P.ProfessorName, C.CourseName, T.TA_Semester, "
            			+ "T.TA_Section " +
            			"FROM PROFESSOR_15 P, COURSE_15 C, TEACHING_ASSIGNMENT_15 T " +
            			"WHERE P.ProfessorId = T.ProfessorId AND "
            			+ "C.CourseCode = T.CourseCode "+
            			"AND C.DepartmentId = '" + deptId + "';";
            	System.out.println(query);
            	String displayString = retrieveFromTable(query);
            	results.setText(displayString);
            }
        }
        ); 
	//---------------------------------------------------------- 
     	// Attach handlers to button using anonymous class
        q5.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
            	String stuId = bannerId5TF.getText();
            	String query = "SELECT S.StudentName, C.CourseName, T.TR_Semester, T.TR_Section, T.TR_Grade "+
            			"FROM STUDENT_15 S, COURSE_15 C, TRANSCRIPT_15 T "+
            			"WHERE S.BannerId = T.StudentId AND C.CourseCode = T.CourseCode "+
            			"AND T.StudentId = '"+ stuId + "';"; 
            	System.out.println(query);
            	String displayString = retrieveFromTable(query);
            	results.setText(displayString);
            }
        }
        );  
	//----------------------------------------------------------
     	// Attach handlers to button using anonymous class
        q6.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
            	String deptId = deptId6TF.getText();
            	String deptName = deptName6TF.getText();
            	String query = "INSERT INTO DEPARTMENT_15 VALUES ('" + 
            	deptId +"', '" + deptName + "');"; 
            	System.out.println(query);
            	insertIntoTable(query);
            	
            	String displayString = "Row inserted Successfully";
            	results.setText(displayString);
            }
        }
        );  
	//----------------------------------------------------------
     	// Attach handlers to button using anonymous class
        q7.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
            	String stuId = studentId7TF.getText();
            	String newStatus = status7TF.getText();
            	String query = "UPDATE STUDENT_15 " + " SET Status = '"+ newStatus + 
            			"' WHERE BannerId = '" + stuId + "';"; 
            	System.out.println(query);
            	updateTable(query);	
            	
            	String displayString = "Row(s) updated Successfully";
            	results.setText(displayString);
            }
        }
        );  
	//----------------------------------------------------------
     	// Attach handlers to button using anonymous class
        q8.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
            	String deptId = deptId8TF.getText();
            	String query = "DELETE FROM DEPARTMENT_15 WHERE DepartmentId = '" + deptId + "';";
            	System.out.println(query);
            	deleteFromTable(query);
            	
            	String displayString = "Row(s) deleted Successfully";
            	results.setText(displayString);
            }
        }
        );  
        
        quit.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
            	System.exit(0);
            }
        }
        );  
    }
    
    //----------------------------------------------------------
    public void start(Stage primaryStage)
    {
        createGUIComponents();
        attachHandlers();
        
        //Create a scene and place it on stage
        scene = new Scene(pane);
        primaryStage.setTitle("College Database");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    //----------------------------------------------------------
    public static void main(String[] args)
    {
        Application.launch(args);
    }
    
    //----------------------------------------------------------
    /**
     * Converts a Vector of properties to a printable String
     * @param data
     * @return
     */
    private static String vecToString(Vector<Properties> data)
	{
		String result = "";
		// Now, we have to print out these rows in a user-understandable form
		if ((data == null) || (data.size() == 0))
		{
			return ("No results were returned from database for this query");
		}
		else
		{
			// Print the headers
			result = ("==============================================\n");
			Properties p1 = data.firstElement();
			Enumeration props1 = p1.propertyNames();
			while (props1.hasMoreElements())
                	   result += (props1.nextElement()+"\t");
			result += "\n";
			result += ("----------------------------------------------\n");
			
			// Now go thru the entire 'data' Vector, get each Properties object out of it
			// and print out the contents of the Properties object
			for (Properties p : data)
        		{
            			Enumeration props = p.propertyNames();
            			while (props.hasMoreElements())
                			result += (p.getProperty((String)(props.nextElement()))+"\t");
            			result += "\n";
        		}
			result += ("==============================================");
		}
		return result;
	}
    
    
  	//----------------------------------------------------------------------------
    //-------------------------------------------------------------------------------
    public static String retrieveFromTable(String queryString)
    {
    	// First, set up an instance of the DatabaseAccessor class
    	DatabaseAccessor dbAcc = new DatabaseAccessor();
    		
    	// Now that you have created the query string, you set that on the DatabaseAccessor
    	// object you created using the 'setSQLStatement()' method as shown below 
    	dbAcc.setSQLStatement(queryString);
    		
    	// Then invoke the method 'executeSQLSelectStatement()' on the DatabaseAccessor object
    	// as shown below to run the query. The result of running this query is a Vector of
    	// Properties objects. Each Properties object in this Vector contains the data from
    	// one of the db table rows matching the query
    	Vector<Properties> returnedValues = dbAcc.executeSQLSelectStatement();
    	
    	return vecToString(returnedValues);
    	// Print the results
    	//printValues(returnedValues);
    }

  	//----------------------------------------------------------------------------
  	
  	public static void insertIntoTable(String insertQueryString )
  	{
  		DatabaseMutator dbMut = new DatabaseMutator();
  		
  		dbMut.setSQLStatement(insertQueryString);
  		Integer returnedValue = dbMut.executeSQLMutateStatement();
  		
  		if (returnedValue != 1)
  			System.out.println("Error in db insertion");
  		else
  			System.out.println("Row inserted successfully");
  	}
  	
  	
  	
  	//----------------------------------------------------------------------------
  	public static void updateTable(String updateQueryString)
  	{
  		DatabaseMutator dbMut = new DatabaseMutator();
  		
  		dbMut.setSQLStatement(updateQueryString);
  		Integer returnedValue = dbMut.executeSQLMutateStatement();
  		
  		if (returnedValue < 0)
  			System.out.println("Error in db update");
  		else
  			System.out.println("Row updated successfully");
  	}
  	
  	
  	
  	//------------------------------------------------------------------------
  	public static void deleteFromTable(String deleteQueryString)
  	{
  		DatabaseMutator dbMut = new DatabaseMutator();
  		
  		dbMut.setSQLStatement(deleteQueryString);
  		Integer returnedValue = dbMut.executeSQLMutateStatement();
  		
  		if (returnedValue < 0)
  			System.out.println("Error in db Delete");
  		else
  			System.out.println("Row deleted successfully");
  	}
  	
}


//----------------------------------------------------------


 