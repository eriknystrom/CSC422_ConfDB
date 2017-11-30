import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

/**
 * For CSC/CIS 422 class
 *
 * @author (your name)
 * @version (2017)
 */
//----------------------------------------------------------
public class ConfDBTesterGUI extends Application {
    // Declare all the GUI components as instance variables. 
    // Don't create the objects at this point.
    // First we need a GridPane
    private GridPane pane;
    // Q1 Entry
    private TextField timeSlot;
    // Q2 Entry
    private TextField buildingEntry;
    // Q3 Entry
    private TextField paperSubject;
    // Q4 Entry
    private TextField paperSubjectCount;
    // Q7 Entries
    private TextField lastName;
    private TextField firstName;
    // Q8 Entries - New time
    private TextField insStartTime;
    private TextField insEndTime;
    // Q9 Entries - Update time
    private TextField newStartTime;
    private TextField newEndTime;
    private TextField timeSlotId;
    // Q10 Entry - Delete time
    private TextField delTimeSlotId;
    // Q11 Entry
    private TextField authFirstName;
    private TextField authLastName;
    // Q112 Entry
    private TextField roomNumber;

    // We need a button
    private Button q1;
    private Button q2;
    private Button q3;
    private Button q4;
    private Button q5;
    private Button q6;
    private Button q7;
    private Button q8;
    private Button q9;
    private Button q10;
    private Button q11;
    private Button q12;
    private Button quit;

    private TextArea results;

    // We need a scene
//    private Scene scene;

    public static void main(String[] args) {
        Application.launch(args);
    }

    /**
     * Converts a Vector of properties to a printable String
     *
     * @param data
     * @return
     */
    private static String vecToString(Vector<Properties> data) {
        String result = "";
        // Now, we have to print out these rows in a user-understandable form
        if ((data == null) || (data.size() == 0)) {
            return ("NO DATA");
        } else {
            // Print the headers
            result = ("==============================================\n");
            Properties p1 = data.firstElement();
            Enumeration props1 = p1.propertyNames();
            while (props1.hasMoreElements())
                result += (props1.nextElement() + "\t");
            result += "\n";
            result += ("----------------------------------------------\n");
            // Now go thru the entire 'data' Vector, get each Properties object out of it
            // and print out the contents of the Properties object
            for (Properties p : data) {
                Enumeration props = p.propertyNames();
                while (props.hasMoreElements())
                    result += (p.getProperty((String) (props.nextElement())) + "\t");
                result += "\n";
            }
            result += ("==============================================");
        }
        return result;
    }

    private static String vecToStringNoHeaders(Vector<Properties> data) {
        String result = "";
        if (data == null || data.isEmpty()) {
            return ("NO DATA");
        } else {
            for (Properties p : data) {
                Enumeration props = p.propertyNames();
                while (props.hasMoreElements())
                    result += (p.getProperty((String) (props.nextElement())));
            }
        }

        return result;
    }

    private static String retrieveFromTable(String queryString, Boolean headers) {
        // First, set up an instance of the DatabaseAccessor class
        DatabaseAccessor dbAcc = new DatabaseAccessor();

        // Now that you have created the query string, you set that on the DatabaseAccessor
        // object you created using the 'setSQLStatement()' method as shown below
        dbAcc.setSQLStatement(queryString);

        // Then invoke the method 'executeSQLSelectStatement()' on the DatabaseAccessor object
        // as shown below to run the query. The result of running this query is a Vector of
        // Properties objects. Each Properties object in this Vector contains the data from
        // one of the db table rows matching the query
//        System.out.println(dbAcc.executeSQLSelectStatement());
        Vector<Properties> returnedValues = dbAcc.executeSQLSelectStatement();
        System.out.println(returnedValues);
//    	System.out.println(returnedValues);
        if (headers == Boolean.TRUE) {
            return vecToString(returnedValues);
        } else {
            return vecToStringNoHeaders(returnedValues);
        }
        // Print the results
        //printValues(returnedValues);
    }

    private static Vector<Properties> retrieveFromQuery(String queryString) {
        DatabaseAccessor dbAcc = new DatabaseAccessor();
        dbAcc.setSQLStatement(queryString);

        return dbAcc.executeSQLSelectStatement();
    }

    private static String retrieveKeyedItem(String item, Vector<Properties> propertiesVector) {
        for (Properties property : propertiesVector) {
            Enumeration prop = property.propertyNames();
            while (true) {
                if (!(prop.hasMoreElements())) break;
                String nextItem = (String) prop.nextElement();
                String value = property.getProperty(nextItem);
                if (item.equals(nextItem)) return value;
            }
        }
        return null;
    }

    private static String retrieveItemFromQuery(String queryString, String item) {

        return null;
    }

    private static void insertIntoTable(String insertQueryString) {
        DatabaseMutator dbMut = new DatabaseMutator();

        dbMut.setSQLStatement(insertQueryString);
        Integer returnedValue = dbMut.executeSQLMutateStatement();

        if (returnedValue != 1)
            System.out.println("Error in db insertion");
        else
            System.out.println("Row inserted successfully");
    }

    private static void updateTable(String updateQueryString) {
        DatabaseMutator dbMut = new DatabaseMutator();

        dbMut.setSQLStatement(updateQueryString);
        Integer returnedValue = dbMut.executeSQLMutateStatement();

        if (returnedValue < 0)
            System.out.println("Error in db update");
        else
            System.out.println("Row updated successfully");
    }

    private static void deleteFromTable(String deleteQueryString) {
        DatabaseMutator dbMut = new DatabaseMutator();

        dbMut.setSQLStatement(deleteQueryString);
        Integer returnedValue = dbMut.executeSQLMutateStatement();

        if (returnedValue < 0)
            System.out.println("Error in db Delete");
        else
            System.out.println("Row deleted successfully");
    }

    // create GUI components
    private void createGUIComponents() {
        //Create GUI Objects
        pane = new GridPane();


        //Create Labels
        Label main_label = new Label("DISPLAY INFORMATION");
        Label q1Label = new Label("Q1. Papers in entered time-slot");
        Label q2Label = new Label("Q2. Papers scheduled in entered building");
        Label q3Label = new Label("Q3. Papers matching entered category ");
        Label q4Label = new Label("Q4. Number of papers in specified category ");
        Label q5Label = new Label("Q5. List all Session Chairs");
        Label q6Label = new Label("Q6. Display sessions information ");
        Label q7Label = new Label("Q7. Papers by specified author");
        Label q8Label = new Label("Q8. Insert new time slot from a to b");
        Label q9Label = new Label("Q9. Update specified time slot");
        Label q10Label = new Label("Q10. Delete specified time slot");
        Label q11Label = new Label("Q11. Number papers by Author");
        Label q12Label = new Label("Q12. Building(s) where room# is");

        // Create the text fields
        // Q1 Fields
        timeSlot = new TextField("<time slot>");
        //Q2 Fields
        buildingEntry = new TextField("<building>");
        //Q3 Fields
        paperSubject = new TextField("<subject>");
        // Q4 Fields
        paperSubjectCount = new TextField("<subject>");
        // Q7 Fields
        lastName = new TextField("<last name>");
        firstName = new TextField("<first name>");
        // Q8 Fields
        insStartTime = new TextField("<start time>");
        insEndTime = new TextField("<end time>");
        //Q9 Fields
        newStartTime = new TextField("<start>");
        newEndTime = new TextField("<end>");
        timeSlotId = new TextField("<id>");
        //Q10 Fields
        delTimeSlotId = new TextField("<id>");
        //Q11 Fields
        authLastName = new TextField("<last name>");
        authFirstName = new TextField("<first name>");
        //Q12 Fields
        roomNumber = new TextField("<room #>");

        // Create the buttons
        q1 = new Button("Q1");
        q2 = new Button("Q2");
        q3 = new Button("Q3");
        q4 = new Button("Q4");
        q5 = new Button("Q5");
        q6 = new Button("Q6");
        q7 = new Button("Q7");
        q8 = new Button("Q8");
        q9 = new Button("Q9");
        q10 = new Button("Q10");
        q11 = new Button("Q11");
        q12 = new Button("Q12");
        quit = new Button("Quit");

        // Create the text area to display results
        results = new TextArea();


        //Add the components to the pane
        pane.add(main_label, 0, 0);
        pane.add(q1Label, 0, 1);
        pane.add(timeSlot, 1, 1);
        pane.add(q1, 4, 1);

        pane.add(q2Label, 0, 2);
        pane.add(buildingEntry, 1, 2);
        pane.add(q2, 4, 2);

        pane.add(q3Label, 0, 3);
        pane.add(paperSubject, 1, 3);
        pane.add(q3, 4, 3);

        pane.add(q4Label, 0, 4);
        pane.add(paperSubjectCount, 1, 4);
        pane.add(q4, 4, 4);

        pane.add(q5Label, 0, 5);
        pane.add(q5, 4, 5);

        pane.add(q6Label, 0, 6);
        pane.add(q6, 4, 6);

        pane.add(q7Label, 0, 7);
        pane.add(lastName, 1, 7);
        pane.add(firstName, 2, 7);
        pane.add(q7, 4, 7);

        pane.add(q8Label, 0, 8);
        pane.add(insStartTime, 1, 8);
        pane.add(insEndTime, 2, 8);
        pane.add(q8, 4, 8);

        pane.add(q9Label, 0, 9);
        pane.add(newStartTime, 1, 9);
        pane.add(newEndTime, 2, 9);
        pane.add(timeSlotId, 3, 9);
        pane.add(q9, 4, 9);

        pane.add(q10Label, 0, 10);
        pane.add(delTimeSlotId, 1, 10);
        pane.add(q10, 4, 10);

        pane.add(q11Label, 0, 11);
        pane.add(authLastName, 1, 11);
        pane.add(authFirstName, 2, 11);
        pane.add(q11, 4, 11);

        pane.add(q12Label, 0, 12);
        pane.add(roomNumber, 1, 12);
        pane.add(q12, 4, 12);

        pane.add(quit, 4, 13);
        pane.add(results, 0, 13, 3, 1);


        // Set Properties
        pane.setAlignment(Pos.CENTER);
    }

    //Attach handlers
    private void attachHandlers() {
        q1.setOnAction(new EventHandler<ActionEvent>() {
                           public void handle(ActionEvent e) {
                               String paper_query = String.format("SELECT T.StartTime, T.EndTime, COUNT(P.PaperId)" +
                                       " as Papers FROM TIME_SLOT T, PAPER P WHERE" +
                                       " (P.StartTime=T.StartTime AND T.TimeSlotId=%s);", timeSlot.getText());

                               Vector<Properties> returnString = retrieveFromQuery(paper_query);
                               String starttime = retrieveKeyedItem("starttime", returnString);
                               String endtime = retrieveKeyedItem("endtime", returnString);
                               String papers = retrieveKeyedItem("papers", returnString);
                               String displayString = String.format("Number of papers at time slot %s (%s - %s): %s",
                                       timeSlot.getText(),
                                       starttime,
                                       endtime,
                                       papers);
                               results.setText(displayString);
                           }
                       }
        );
        q2.setOnAction(new EventHandler<ActionEvent>() {
                           public void handle(ActionEvent e) {

                               String building = buildingEntry.getText();
                               String query = String.format("SELECT COUNT(BuildingId) FROM SESSION_ROOM_CHAIR" +
                                       " WHERE BuildingId=(SELECT Id FROM BUILDING WHERE Name='%s');", building);
                               String displayString = String.format("Papers in %s: %s",
                                       building, retrieveFromTable(query, Boolean.FALSE));
                               results.setText(displayString);
                           }
                       }
        );
        q3.setOnAction(new EventHandler<ActionEvent>() {
                           public void handle(ActionEvent e) {
                               String subject = paperSubject.getText();
                               String query = String.format("SELECT * FROM PAPER WHERE SubjectId=" +
                                       "(SELECT SubjectId FROM SUBJECT_CATEGORY Where description='%s')" +
                                       " LIMIT 10;", subject);
                               String displayString = retrieveFromTable(query, Boolean.TRUE);
                               results.setText(displayString);
                           }
                       }
        );
        q4.setOnAction(new EventHandler<ActionEvent>() {
                           public void handle(ActionEvent e) {

                               String subject = paperSubjectCount.getText();
                               String query = String.format("SELECT COUNT(*) as 'Number Papers In %s' FROM PAPER" +
                                       " WHERE SubjectId=(SELECT SubjectId FROM SUBJECT_CATEGORY " +
                                       "WHERE description='%<s');", subject);
                               System.out.println(query);
                               String displayString = String.format("Number of papers in %s: %s", subject,
                                       retrieveFromTable(query, Boolean.FALSE));
                               results.setText(displayString);
                           }
                       }
        );
        // Attach handlers to button using anonymous class
        q5.setOnAction(new EventHandler<ActionEvent>() {
                           public void handle(ActionEvent e) {
                               String query = "SELECT P.PersonId, P.LastName, P.FirstName FROM SESSION_CHAIR S," +
                                       " PERSON P WHERE P.PersonId=S.SessionChairId ORDER BY P.LastName ASC;";
                               System.out.println(query);
                               String displayString = retrieveFromTable(query, Boolean.TRUE);
                               results.setText(displayString);
                           }
                       }
        );
        // Attach handlers to button using anonymous class
        q6.setOnAction(new EventHandler<ActionEvent>() {
                           public void handle(ActionEvent e) {
                               String query = "SELECT S.SessionId, S.RoomId, B.Name FROM SESSION_ROOM_CHAIR S," +
                                       " BUILDING B WHERE B.Id=S.BuildingId ORDER BY S.SessionId LIMIT 10;";
                               System.out.println(query);
                               String displayString = retrieveFromTable(query, Boolean.TRUE);
                               results.setText(displayString);
                           }
                       }
        );
        // Attach handlers to button using anonymous class
        q7.setOnAction(new EventHandler<ActionEvent>() {
                           public void handle(ActionEvent e) {
                               String last_name = lastName.getText();
                               String first_name = firstName.getText();
                               String query = String.format("SELECT Title, LastName, FirstName, Description, SessionId, StartTime," +
                                       " EndTime FROM SUBJECT_CATEGORY SC JOIN PAPER P ON SC.SubjectId = P.SubjectId" +
                                       " JOIN PERSON PN ON PN.PersonId = P.ContactAuthorId WHERE LastName = '%s'" +
                                       " AND FirstName = '%s';", last_name, first_name);
                               System.out.println(query);
                               String displayString = retrieveFromTable(query, Boolean.TRUE);
                               results.setText(displayString);
                           }
                       }
        );
        // Attach handlers to button using anonymous class
        q8.setOnAction(new EventHandler<ActionEvent>() {
                           public void handle(ActionEvent e) {
                               String start = insStartTime.getText();
                               String end = insEndTime.getText();
                               String queryOne = "SET @timeid = (SELECT MAX(TimeSlotId) FROM TIME_SLOT)+1;";
                               String time = retrieveFromTable(queryOne, Boolean.FALSE);
                               String queryTwo = String.format("INSERT INTO TIME_SLOT VALUES (@timeid, '%s', '%s');", start, end);
                               System.out.println(queryTwo);
                               insertIntoTable(queryTwo);

                               String displayString = queryOne + "\n" + queryTwo +
                                       "\n\nRow(s) inserted Successfully";
                               results.setText(displayString);
                           }
                       }
        );
        q9.setOnAction(new EventHandler<ActionEvent>() {
                           public void handle(ActionEvent e) {
                               String newStart = newStartTime.getText();
                               String newEnd = newEndTime.getText();
                               String timeSlot = timeSlotId.getText();
                               String query = String.format("UPDATE TIME_SLOT SET StartTime='%s'," +
                                       " EndTime='%s' WHERE TimeSlotId=%s;", newStart, newEnd, timeSlot);
                               System.out.println(query);
                               updateTable(query);

                               String displayString = query +
                                       "\n\nRow(s) updated Successfully";
                               results.setText(displayString);
                           }
                       }
        );
        q10.setOnAction(new EventHandler<ActionEvent>() {
                            public void handle(ActionEvent e) {
                                String timeSlot = delTimeSlotId.getText();
                                String query = String.format("DELETE FROM TIME_SLOT WHERE TimeSlotId='%s';", timeSlot);
                                System.out.println(query);
                                deleteFromTable(query);

                                String displayString = query +
                                        "\n\nRow(s) deleted Successfully";
                                results.setText(displayString);
                            }
                        }
        );
        q11.setOnAction(new EventHandler<ActionEvent>() {
                            public void handle(ActionEvent e) {
                                String firstName = authFirstName.getText();
                                String lastName = authLastName.getText();
                                String query = String.format("SELECT COUNT(Title) FROM SUBJECT_CATEGORY JOIN PAPER" +
                                        " ON SUBJECT_CATEGORY.SubjectId = PAPER.SubjectId JOIN PERSON ON" +
                                        " PERSON.PersonId = PAPER.ContactAuthorId " +
                                        "WHERE LastName = '%s' AND FirstName = '%s';", lastName, firstName);
                                System.out.println(query);
                                String displayString = retrieveFromTable(query, Boolean.TRUE);
                                results.setText(displayString);
                            }
                        }
        );
        q12.setOnAction(new EventHandler<ActionEvent>() {
                            public void handle(ActionEvent e) {
                                String roomNum = roomNumber.getText();
                                String query = String.format("SELECT BUILDING.Name FROM BUILDING," +
                                        " ROOM WHERE ROOM.BuildingId = BUILDING.Id AND ROOM.RoomId = '%s';", roomNum);
                                System.out.println(query);
                                String displayString = retrieveFromTable(query, Boolean.TRUE);
                                results.setText(displayString);
                            }
                        }
        );

        quit.setOnAction(new EventHandler<ActionEvent>() {
                             public void handle(ActionEvent e) {
                                 System.exit(0);
                             }
                         }
        );
    }

    public void start(Stage primaryStage) {
        createGUIComponents();
        attachHandlers();

        //Create a scene and place it on stage
        Scene scene = new Scene(pane);
        primaryStage.setTitle("Conference Database");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}