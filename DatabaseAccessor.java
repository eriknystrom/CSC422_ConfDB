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

import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

// project imports
// import event.Event;

// Beginning of DatabaseAccessor class
//==============================================================
public class DatabaseAccessor extends DatabaseManipulator
{

    //----------------------------------------------------------
    public DatabaseAccessor()
    {
	  super();
    }
	

    /**
     * This handles only equality in the WHERE clause and assumes
     * that no type indication is necessary due to the fact that
     * all values are of Text type
     * 
     *
     * returns a Vector with each element being a Properties object
     * containing the columnName=columnValue mappings
     */
    //------------------------------------------------------------
    public Vector<Properties> getMultiValuedSelectResult(String fieldList,
					String tableName, 
					Properties whereValues)
    {
		return getMultiValuedSelectResult(fieldList, tableName,	whereValues, null);
    }

    /**
     *
     * This handles only equality in the WHERE clause. This also 
     * expects that for numeric types in the WHERE clause, a separate
     * Properties object containing the column name and numeric type
     * indicator will be provided. For text types, no entry in this
     * Properties object is necessary.
     * 
     *
     * returns a Vector with each element being a Properties object
     * containing the columnName=columnValue mappings
     */
    //------------------------------------------------------------
    public Vector<Properties> getMultiValuedSelectResult(String fieldList,
					String tableName, 
					Properties whereValues,
					Properties whereTypes)
    {
		// Begin construction of the actual SQL statement
		theSQLStatement = "SELECT " + fieldList + " FROM " + tableName;

		// Construct the WHERE part of the SQL statement
		String theWhereString = "";
		
		// Now, traverse the WHERE clause Properties object
		if (whereValues != null)
		{
			Enumeration theWhereColumns = whereValues.propertyNames();
			while (theWhereColumns.hasMoreElements() == true)
			{
				if (theWhereString.equals(""))
				{
		  			theWhereString += " WHERE ";
				}
				else
				{
					theWhereString += " AND ";
				}
	
				String theColumnName = (String)theWhereColumns.nextElement();
				String theColumnValue = insertEscapes(whereValues.getProperty(theColumnName));
	
				if (theColumnValue.equals("NULL"))
				{
					theWhereString += theColumnName + " IS NULL";
				}
				else
				{
					String actualType = "Text";
					if (whereTypes != null)
					{
						String whereTypeValue = whereTypes.getProperty(theColumnName);
						if (whereTypeValue != null)
						{
							actualType = whereTypeValue;
						}
					}
					actualType = actualType.toLowerCase();
	
					if (actualType.equals("numeric") == true)
					{
						theWhereString += theColumnName + " = " + theColumnValue;
					}
					else
					{
						theWhereString += theColumnName + " = '" + theColumnValue + "'";
	
					}	
				}
			}
		}
		  
		theSQLStatement += theWhereString;
		
		theSQLStatement += ";";
		
		return executeSQLSelectStatement();
	}

    /**
     *
     * returns a Vector with each element being a Properties object
     * containing the columnName=columnValue mappings
     */
    //------------------------------------------------------------
    public Vector<Properties> executeSQLSelectStatement()
    {
		int numRSColumns = 0; //number of columns in ResultSet
		Vector<String> namesRSColumns = null; // names of columns in ResultSet
		
		
		try
		{
			setUpDBConnection();
			ResultSet rs = (ResultSet)executeTheActualStatement();			
			
			if (rs != null)
			{
				// get the column information from the ResultSet
				ResultSetMetaData rsMetaData = rs.getMetaData();
	
				numRSColumns = rsMetaData.getColumnCount();
				namesRSColumns = new Vector<String>();
				for (int cnt = 1; cnt <= numRSColumns; cnt++)
				{
					String thisColumnName = rsMetaData.getColumnName(cnt);
					namesRSColumns.addElement(thisColumnName);
				}
			
				Vector<Properties> multiValuedSetToReturn = new Vector<Properties>();
			
				while (rs.next() == true)
				{
					Properties thisRow = new Properties();
					for (int cnt = 1; cnt <= numRSColumns; cnt++)
					{
						String theColumnName = (String)namesRSColumns.elementAt(cnt-1);
						String theColumnValue = rs.getString(cnt);					
						
						// The value of the column might be NULL. In that case, we DON'T
						// put it into the Properties object
						if (theColumnValue != null)
						{
							thisRow.setProperty(theColumnName.toLowerCase(), theColumnValue);
						}
					}
					multiValuedSetToReturn.addElement(thisRow);
				}
	
				return multiValuedSetToReturn;
			}

			return null;
		}
		catch (SQLException sqle)
		{
		/* System.err.println( "An SQL Error Occured:" + sqle + "\n" +
            	sqle.getErrorCode() + "\n" + sqle.getMessage() + "\n"
			+ sqle); */
			//new Event(Event.GetLeafLevelClassName(this), "executeSQLSelectStatement", "SQL Exception: " + sqle.getErrorCode() + ": " + sqle.getMessage(), Event.ERROR);
			
			return null;
		}
		finally
		{
			closeStatement();
			freeDBConnection();
		}
	}
   
   
    //------------------------------------------------------------
    protected Object executeTheActualStatement() throws SQLException
    {
		if (theDBConnection != null)
		{
			// Once a connection has been established we can create an instance
			// of Statement, through which we will send queries to the database.
			// Only the Global Pool connection should be used!

			theStatement = theDBConnection.createStatement();
			
			// Stop Runaway Queries
			theStatement.setMaxRows(10000);
			
			// The method executeQuery executes a query on the database. The
			// return result is of type ResultSet which is one or more rows in
			// this case.
			if (theSQLStatement != null)
			{
				ResultSet theResultSet = theStatement.executeQuery(theSQLStatement);
				
				return theResultSet;
			}
		}
        return null;
    }
}


//---------------------------------------------------------------
//	Revision History:
//
//	$Log: DatabaseAccessor.java,v $
//	Revision 1.3  2003/08/12 19:21:47  tomb
//	Updated format, added revision block.
//	
