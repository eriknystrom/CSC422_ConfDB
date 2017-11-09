// tabs=4
//************************************************************
//	COPYRIGHT 2003 ArchSynergy, Ltd. - ALL RIGHTS RESERVED
//
// This file is the product of ArchSynergy, Ltd. and cannot be 
// reproduced, copied, or used in any shape or form without 
// the express written consent of ArchSynergy, Ltd.
//
// This file is being used for INSTRUCTIONAL PURPOSES ONLY with 
// the permission of ArchSynergy, Ltd.
//
//*************************************************************

/** @author		$Author: tomb $ */
/** @version	$Revision: 1.3 $ */


// specify the package

// system imports
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

//import java.sql.*;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
//import java.io.*;


// project imports
//import Event.Event;
import database.JDBCBroker;


// Beginning of DatabaseManipulator class
//---------------------------------------------------------------------------------------------------------
abstract public class DatabaseManipulator
{

    protected static JDBCBroker myBroker = JDBCBroker.getInstance();

    protected Statement theStatement = null;
    protected Connection theDBConnection = null;
    protected String theSQLStatement = null;

    //------------------------------------------------------------
    public DatabaseManipulator()
    {
        theStatement = null;
        theDBConnection = null;
    }
	
    //-----------------------------------------------------------
    public void setSQLStatement(String sql)
    {
        theSQLStatement = sql;
    }
   
    //------------------------------------------------------------
    protected void setUpDBConnection()
    {
	  theDBConnection = myBroker.getConnection();
    }

    //------------------------------------------------------------
    protected void freeDBConnection()
    {
	  myBroker.releaseConnection(theDBConnection);
    }

    //------------------------------------------------------------
    protected void closeStatement()
    {
		try
		{
			if (theStatement != null)
			{
			theStatement.close();
			}
		}
		catch (SQLException sqle)
		{
			//new Event(Event.GetLeafLevelClassName(this), "closeStatement", "SQL Exception: " + sqle.getErrorCode() + ": " + sqle.getMessage(), Event.ERROR);
		}
    }

    /**
     * In order to facilitate having apostrophe's in the data in the DB table
     * columns, we need to insert the '\\' as escape string in the data values. This method
     * accomplishes that.
     * 
     */
	//----------------------------------------------------------------------
	public String insertEscapes(String inString)
	{
		String outString = "";
		
		char characterToEscape = '\'';
		
		String escapeString = "\\";
		
		int inStringLen = inString.length();
		
		int indexOfEscapeChar = inString.indexOf(characterToEscape);
		
		boolean allDone = (indexOfEscapeChar == -1);

		while (allDone == false) // in other words, there is still an escape char to handle
		{
			String prefix = inString.substring(0, indexOfEscapeChar);
			outString += prefix;
			outString += escapeString;
			outString += inString.charAt(indexOfEscapeChar);
			
			if (indexOfEscapeChar + 1 >= inStringLen)
			{
				allDone = true;
				inString = "";
			}
			else
			{
				inString = inString.substring(indexOfEscapeChar + 1);
				indexOfEscapeChar = inString.indexOf(characterToEscape);
				allDone = (indexOfEscapeChar == -1);
			}
		} // while

		outString += inString;
		
		return outString;
	}

    /**
     * The method below returns an object, which either contains a result
     * set if the associated SQL statement is a SELECT statement, or the
     * return code, indicating the number of rows inserted, updated or deleted
     * if the statement is an INSERT, UPDATE or DELETE statement. The return
     * code is encapsulated in an Integer object.
     */
    //------------------------------------------------------------
    abstract protected Object executeTheActualStatement() throws SQLException;       
}


//---------------------------------------------------------------
//	Revision History:
//
//	$Log: DatabaseManipulator.java,v $
//	Revision 1.3  2003/08/12 19:21:47  tomb
//	Updated format, added revision block.
//	
