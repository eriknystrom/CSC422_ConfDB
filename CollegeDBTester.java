//

//system imports
import java.util.Enumeration;
import java.util.Properties;
import java.util.Scanner;
import java.util.Vector;

import java.io.IOException;

//test
// Tester for the Java-DB Connection using College DB
//====================================================================================
public class CollegeDBTester {
	
	//-------------------------------------------------------------------------------
	// Printing the result-set in a readable format
	//-------------------------------------------------------------------------------
	public static Scanner sc = new Scanner(System.in);
	private static void printValues(Vector<Properties> data)
	{
		// Now, we have to print out these rows in a user-understandable form
		if ((data == null) || (data.size() == 0))
		{
			System.out.println("No results were returned from database for this query");
		}
		else
		{
			// Print the headers
			System.out.println("==============================================");
			Properties p1 = data.firstElement();
			Enumeration props1 = p1.propertyNames();
			while (props1.hasMoreElements())
                	   System.out.print(props1.nextElement()+"\t");
			System.out.println();
			System.out.println("----------------------------------------------");
			
			// Now go thru the entire 'data' Vector, get each Properties object out of it
			// and print out the contents of the Properties object
			for (Properties p : data)
        		{
            			Enumeration props = p.propertyNames();
            			while (props.hasMoreElements())
                			System.out.print(p.getProperty((String)(props.nextElement()))+"\t");
            			System.out.println();
        		}
			System.out.println("==============================================");
		}
	}
	//-------------------------------------------------------------------------------
	// This is a generic SELECT statement when the user knows how to write SQL code
	//-------------------------------------------------------------------------------
	public static void retrieveFromTable(String queryString)
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
		
		// Print the results
		printValues(returnedValues);
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
	
	public static void handleQuery1()
	{
	    String query = "SELECT * FROM  AUTHOR ";
	    System.out.println(query);
	    DatabaseAccessor dbAcc = new DatabaseAccessor();
	    dbAcc.setSQLStatement(query);
	    Vector<Properties> returnedValues = dbAcc.executeSQLSelectStatement();		
	    printValues(returnedValues);	
	}
	
	public static void handleQuery2()
	{
	    System.out.println("Enter status of the student like Freshman, "+
		"Sophomore, " + "Junior or " + "Senior ");
	    String status = sc.nextLine();
	    String query = "SELECT BannerId, StudentName FROM STUDENT_15 WHERE Status = '"+
                    status + "';"; 
	    System.out.println(query);
	    DatabaseAccessor dbAcc = new DatabaseAccessor();
	    dbAcc.setSQLStatement(query);
	    Vector<Properties> returnedValues = dbAcc.executeSQLSelectStatement();		
	    printValues(returnedValues);	
	}

	public static void handleQuery3()
	{
	    System.out.println("Enter the Dept. Id. like MTH pr PHS: ");
	    String deptId = sc.nextLine();
	    String query = "SELECT P.ProfessorId, P.ProfessorName, D.DepartmentName FROM PROFESSOR_15 P, DEPARTMENT_15 D "+ 
  		"WHERE P.DepartmentId = D.DepartmentId AND P.DepartmentId = '" + deptId +"';"; 
	    System.out.println(query);
	    DatabaseAccessor dbAcc = new DatabaseAccessor();
	    dbAcc.setSQLStatement(query);
	    Vector<Properties> returnedValues = dbAcc.executeSQLSelectStatement();		
	    printValues(returnedValues);	
	}

	public static void handleQuery4()
	{
	    System.out.println("Enter the Dept. Id. like MTH pr PHS: ");
	    String deptId = sc.nextLine();
	    String query = "SELECT P.ProfessorName, C.CourseName, T.TA_Semester, T.TA_Section " +
  		    "FROM PROFESSOR_15 P, COURSE_15 C, TEACHING_ASSIGNMENT_15 T " +
  		    "WHERE P.ProfessorId = T.ProfessorId AND C.CourseCode = T.CourseCode "+
		    "AND C.DepartmentId = '" + deptId + "';";
	    System.out.println(query);
	    DatabaseAccessor dbAcc = new DatabaseAccessor();
	    dbAcc.setSQLStatement(query);
	    Vector<Properties> returnedValues = dbAcc.executeSQLSelectStatement();		
	    printValues(returnedValues);	
	}

	public static void handleQuery5()
	{
	    System.out.println("Enter Student Id: ");
	    String stuId = sc.nextLine();
	    String query = "SELECT S.StudentName, C.CourseName, T.TR_Semester, T.TR_Section, T.TR_Grade "+
  		    "FROM STUDENT_15 S, COURSE_15 C, TRANSCRIPT_15 T "+
  		    "WHERE S.BannerId = T.StudentId AND C.CourseCode = T.CourseCode "+
		    "AND T.StudentId = '"+ stuId + "';"; 
	    System.out.println(query);
	    DatabaseAccessor dbAcc = new DatabaseAccessor();
	    dbAcc.setSQLStatement(query);
	    Vector<Properties> returnedValues = dbAcc.executeSQLSelectStatement();		
	    printValues(returnedValues);	
	}

	public static void handleQuery6()
	{
	    System.out.println("Enter the Department Id: ");
	    String deptId = sc.nextLine();
	    System.out.println("Enter the Department Name: ");
	    String deptName = sc.nextLine();
	    String query = "INSERT INTO DEPARTMENT_15 VALUES ('" + deptId +"', " +
 			   deptName + "');"; 
	    System.out.println(query);
	    insertIntoTable(query);	
	}

	public static void handleQuery7()
	{
	    System.out.println("Enter Student Id: ");
	    String stuId = sc.nextLine();
	    System.out.println("Enter new status of the student: ");
	    String newStatus = sc.nextLine();

	    String query = "UPDATE STUDENT_15 WHERE BannerId = '" + stuId + "' SET Status = '"+
                    newStatus + "');"; 
	    System.out.println(query);
	    updateTable(query);	
	}

	public static void handleQuery8()
	{
	    System.out.println("Enter the Dept. Id. like MTH pr PHS: ");
	    String deptId = sc.nextLine();
	    String query = "DELETE FROM DEPARTMENT_15 WHERE Id = '" + deptId + "');";
 	    System.out.println(query);
	    deleteFromTable(query);	
	}

	private static void handleInteractiveQueries()
	{		
		String building = "";
		String department = "";
		String office = "";
		String query = "";
		String[] englishQuestions = {
			"Show all the information from Professor table",
			"Show the BannerId and Names of all students with this <status>",
			"Show the professor id, professor name, and Department for this <dept id>",		
			"Show the Professor Name, Course Name, Semester and Section for all courses in this <deptId>.",		
			"Show the Student name, Course Name, Semester and Section and grade for the student with this <bannerId>",		
			"Insert a new department",
			"Update a Student Status",
			"Delete a department"
		};
		System.out.println("\n\n---------------------------------------------------");
		System.out.println("Which query do you want answered? Enter 1 .." + englishQuestions.length);
		System.out.println("Enter 0 (zero) to quit \n");
		System.out.println("---------------------------------------------------");
		for (int k = 0; k < englishQuestions.length; k++)
		{
			System.out.println((k+1) + "   "+englishQuestions[k]+"\n");
		}
		System.out.println("---------------------------------------------------");
		String answerString = sc.nextLine();
		int answer = Integer.parseInt(answerString);
		
		if (answer == 0) System.exit(0);
		if (answer == 1)
		{
			handleQuery1();
		}
		else if (answer == 2)
		{
			handleQuery2();			
		}
		else if (answer == 3)
		{
			handleQuery3();
		} 
		else if (answer == 4)
		{
			handleQuery4();			
		} 
		else if (answer == 5)
		{
			handleQuery5();
		} 
		else if (answer == 6)
		{
			handleQuery6();	
		}
		else if (answer == 7)
		{
			handleQuery7();
		}
		else if (answer == 8)
		{
			handleQuery8();
		}
			
	}
	public static void main(String[] args)
	{
		String goOn = "Y";
		goOn = goOn.toLowerCase();
		
		while (goOn.startsWith("y"))
		{
			handleInteractiveQueries();
			System.out.println("Do you wish to continue (y/n)?");
			goOn = sc.nextLine();
			goOn = goOn.toLowerCase();
		}
	}
}
