**CIS/CSC 422:        Database Management Systems (TMR)                DUE: 11-28-17**

**Fall 2017                Group Term Project                                        Points = 50**

**CONFERENCE DATABASE**

**DESCRIPTION and DESIGN**

Our college is organizing a conference and would like to set up a database to manage the information about the conference.

The college has many buildings, such as Edwards, Holmes, Dailey, etc. Each building has several classrooms in which the conference papers are presented. Each room has a room number (something like 201, the first digit indicates the floor and the remaining digits indicate the room number). Some rooms also have a name, such as &quot;Blue Room&quot; or &quot;Indigo Room&quot;, etc. Each room has a capacity, i.e. the maximum number of people it can hold.

The conference takes place over a period of one day. Several parallel sessions are held. Each session is described by a date, a start time and an end time. There are papers presented at each session in the conference. Each paper is described by a paper-id, and a title. There are many people involved in the conference. Each person is identified by an id number, last name, first name and an e-mail. Authors write papers and reviewers have reviewed papers. (It is assumed that this database is for information about accepted papers only). Every paper is about a subject. Paper presentations are scheduled in sessions. Each session has a session chair, and has many papers scheduled to be present. Each paper is given a time-slot



**Relational Model for the Conference Database**

**BUILDING (****Id****, Name)**

**TILE\_SLOT(****TimeSlotId****, StartTime, EndTime)**

**ROOM(****RoomId, BuildingId****, RoomName, Capacity)**

**FOREIGN KEY (BuildingId) REFERENCES BUILDING.BuildingId**

**SUBJECT\_CATEGORY(****SubjectId****, Description)**

**PERSON(****PersonId****, LastName, FirstName, Email)**

**UNIQUE LN\_FN (LastName, FirstName)**

**REVIEWER(****ReviewerId****)**

**FOREIGN KEY (ReviewerId) REFERENCES PERSON.PersonId**

**SESSION\_CHAIR(****SessionChairId****)**

**FOREIGN KEY (SessionChairId) REFERENCES PERSON.PersonId**

**AUTHOR(AuthorId)**

**FOREIGN KEY (AuthorId) REFERENCES PERSON.PersonId**

**CONTACT\_AUTHOR(ContactAuthorId)**

**FOREIGN KEY (ContactAuthorId) REFERENCES AUTHOR.AuthorId**

**SESSION\_ROOM\_CHAIR(****SessionId****, TimeSlotId, RoomId, BuildingId, SessionChairId)**

**FOREIGN KEY (TimeSlotId) REFERENCES TIME\_SLOT.TimeSlotId,**

**FOREIGN KEY (RoomId) REFERENCES ROOM.RoomId,**

**FOREIGN KEY (BuildingId) REFERENCES BUILDING.BuildingId,**

**FOREIGN KEY (SessionChairId) REFERENCES SESSION\_CHAIR.SessionChairId**

**PAPER(PaperId, Title, ContactAuthorId, SubjectId, SessionId, StartTime, EndTime)**

**FOREIGN KEY (ContactAuthorId) REFERENCES CONTACT\_AUTHOR.ContactAuthorId,**

**FOREIGN KEY (SubjectId) REFERENCES SUBJECT\_CATEGORY.SubjectId,**

**FOREIGN KEY (SessionId) REFERENCES SESSION\_ROOM\_CHAIR.SessionId**

**PAPER\_REVIEWER(****PaperId****, ReviewerId)**

**FOREIGN KEY (PaperId) REFERENCES PAPER.PaperId,**

**FOREIGN KEY (ReviewerId) REFERENCES REVIEWER.ReviewerId**



**Note:**

Actual Data for these tables is given in a separate file. You are also given a ZIP file which contains the JDBC jar file, and the required supporting classes DatabaseManipulator.java, DatabaseAccessor.java, DatabaseMutator.java and tester program called CollegeDBTesterGUI.java.

**Your Assignment**

**Rename the CollegeDBTesterGUI.java to ConferenceDBTesterGUI.java and adapt the program to work with the Conference database.**

You have to handle these 10 queries. For each query you have to print the SQL and the result set right after that,

1. Show how many papers are in a time-slot. The user enters a number 1 to 5. The system displays start-time, end-time and the number of papers scheduled in that time-slot
2. Show how many papers are scheduled in a &lt;building&gt;. User enters the building name such as &#39;Edwards&#39; and the system displays building name and the number of papers scheduled in that building.
3. Show all the details of papers whose SUBJECT\_CATEGORY description is &lt;subject&gt;. The user enters something like Anthropology for the subject and the system displays all the details of papers in Anthropology. Use LIMIT 10.
4. Show how many papers are there in a subject? (The user enters a description like Chemistry or Biology and it should show the number of papers in that subject)
5. Find the Id, last name, and first name of all session chairs in the alphabetical order of last names.
6. For all sessions, display the SessionId, RoomId and building name of there this session is located. For example, SessionId = 1, is located in RoomId = 102 in BuildingName = Edwards. Use LIMIT 10 – this prints the first 10 lines only.
7. Display the Title, Last Name, First Name of the Contact Author, Subject Category, Session Id, Start Time and End DTime of all papers whose contact author &lt;lastName&gt; &lt;firstName&gt; (For example: LastName  [=](https://csdb.brockport.edu/phpmyadmin/url.php?url=http%3A%2F%2Fdev.mysql.com%2Fdoc%2Frefman%2F5.1%2Fen%2Fcomparison-operators.html%23operator_equal&amp;token=3c41bc427341be10bc69493f5da5206f) &#39;DErasmo&#39;and FirstName  [=](https://csdb.brockport.edu/phpmyadmin/url.php?url=http%3A%2F%2Fdev.mysql.com%2Fdoc%2Frefman%2F5.1%2Fen%2Fcomparison-operators.html%23operator_equal&amp;token=3c41bc427341be10bc69493f5da5206f) &#39;Stefanie&#39;. The user enters last name and first name and the system displays the results.
8. Insert a new Time-Slot: 5:00 PM to 6:00 PM
9. Update the time-slot that was inserted by changing it to 5:30 PM to 6:30 PM
10. Delete the newly updated time-slot: 5:30 PM to 6:30 PM.



**Make 2 select queries of your own. Print the English Query, SQL query and the result set. The query should involve at least two tables.**

**Note:**

1. Modify the Java program to handle the conference database. Just have stubs for the button handlers.
2. Work with the database separately and get the queries to work. Once you are sure that the queries are working embed them into the Java program.
