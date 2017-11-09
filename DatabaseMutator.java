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

/** @author		$Author: smitra $ */
/** @version	$Revision: 1.4 $ */


// specify the package

/// system imports
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import java.sql.SQLException;

// project imports
//import Event.Event;

//import InvalidReturnValueException;

// Beginning of DatabaseMutator class
//---------------------------------------------------------------------------------------------------------
public class DatabaseMutator extends DatabaseManipulator
{

    //------------------------------------------------------------
    public DatabaseMutator()
    {
	  super();
    }
	
    /**
     *
     * This handles only equality in the WHERE clause. This method
     * assumes that all the values are of Text type and so no separate
     * type indicators are necessary
     *
     * returns a count of the number of rows deleted
     */
    //------------------------------------------------------------
    public int deleteFromDatabase( String tableName, Properties whereValues) 
    	throws InvalidReturnValueException
    {
		return deleteFromDatabase( tableName, whereValues, null);
    }

    /**
     *
     * This handles only equality in the WHERE clause. This also
     * assumes that the 'whereTypes' Properties object will contain
     * an indication of numeric types for those column values that are
     * numeric. For Text types, no entry in this Properties object is
     * necessary.
     *
     * returns a count of the number of rows deleted
     */
    //------------------------------------------------------------
    public int deleteFromDatabase(String tableName, 
   							  Properties whereValues,
    							  Properties whereTypes)
    		throws InvalidReturnValueException
    {
		// Begin construction of the actual SQL statement
		theSQLStatement = "DELETE FROM " + tableName;

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

		// DEBUG System.out.println("The delete statement = " + theSQLStatement);

		Integer returnCode = executeSQLMutateStatement();
		
		if (returnCode != null)
		{
			return returnCode.intValue();
		}
		else
		{		
			throw new InvalidReturnValueException("Invalid Return Code From Database"); 
		}
	}

    /**
     *
     * This method enables insertion of a set of values into a table. It
     * assumes that all the values inserted are of Text type
     *
     * returns a count of the number of rows inserted
     */
    //------------------------------------------------------------
    public int insertIntoDatabase( String tableName, Properties insertValues)
    	throws InvalidReturnValueException
    {
		return insertIntoDatabase( tableName, insertValues, null );
    }

    /**
     *
     * This method assumes that the values to be inserted are indicated
     * along with their column names (column names are a MUST - no positional
     * stuff is allowed at this point). Further, if the type of the value 
     * is numeric, that must be indicated in the 'insertTypes' parameter
     *
     * returns a count of the number of rows inserted
     */
	//------------------------------------------------------------
	public int insertIntoDatabase( String tableName, Properties insertValues,
		Properties insertTypes)
		throws InvalidReturnValueException
	{
		// Begin construction of the actual SQL statement
		theSQLStatement = "INSERT INTO " + tableName;
		
		// Construct the column name list and values part of the SQL statement
		String theColumnNamesList = "";
		String theValuesString = "";
		
		// Now, traverse the Properties object. In this case, this loop
		// must go at least one or we will get an error back from the db
		Enumeration theValuesColumns = insertValues.propertyNames();
		while (theValuesColumns.hasMoreElements() == true)
		{
			if ((theValuesString.equals("") == true) && (theColumnNamesList.equals("") == true))
			{
		  		theValuesString += " VALUES ( ";
				theColumnNamesList += " ( ";
			}
			else
			{
				theValuesString += " , ";
				theColumnNamesList += " , ";
			}
		
			String theColumnName = (String)theValuesColumns.nextElement();
			String theColumnValue = insertEscapes(insertValues.getProperty(theColumnName));
		
			theColumnNamesList += theColumnName;
		
			String actualType = "Text";
			if (insertTypes != null)
			{
				String insertTypeValue = insertTypes.getProperty(theColumnName);
				if (insertTypeValue != null)
				{
					actualType = insertTypeValue;
				}
			}
			actualType = actualType.toLowerCase();
		
			if (actualType.equals("numeric") == true)
			{
				theValuesString += theColumnValue;
			}
			else
			{
				theValuesString += "'" + theColumnValue + "'";
			}
		
		} // end while
		
		if ((theValuesString.equals("") == false) && (theColumnNamesList.equals("") == false))
		// this must be the case for an insert statement
		{
			theValuesString += " ) ";
			theColumnNamesList += " ) ";
		}
		
		theSQLStatement += theColumnNamesList;
		theSQLStatement += theValuesString;
		
		theSQLStatement += ";";
		
		Integer returnCode = executeSQLMutateStatement();
		
		if (returnCode != null)
		{
			return returnCode.intValue();
		}
		else
		{
			throw new InvalidReturnValueException("Invalid Return Code From Database"); 
		}	  
	}

    /**
     *
     * This method assumes that the values to be updated are indicated
     * along with their column names. Further, all the update values are
     * of Text type. Same logic applies to the WHERE clause
     *
     * returns a count of the number of rows updated
     */
    //------------------------------------------------------------
    public int updateDatabase( String tableName, Properties updateValues,
		Properties whereValues)
		throws InvalidReturnValueException
    {
		return updateDatabase(tableName, updateValues, null, whereValues, null);
    }

    /**
     *
     * This method assumes that the values to be updated are indicated
     * along with their column names. Further, if the type of the value 
     * is numeric, that must be indicated in the 'updateTypes' parameter. 
     * Same logic applies to the WHERE clause
     *
     * returns a count of the number of rows updated
     */
    //------------------------------------------------------------
    public int updateDatabase( String tableName, Properties updateValues,
		Properties updateTypes, Properties whereValues, Properties whereTypes)
		throws InvalidReturnValueException
    {
	  
		// Begin construction of the actual SQL statement
		theSQLStatement = "UPDATE " + tableName;
		
		// Construct the SET part of the SQL statement
		String theSetString = "";
		
		// Now, traverse the update Properties object (used for creating
		// the SET part of this statement)
		Enumeration theSetColumns = updateValues.propertyNames();
		while (theSetColumns.hasMoreElements() == true)
		{
			if (theSetString.equals(""))
			{
				theSetString += " SET ";
			}
			else
			{
				theSetString += " , ";
			}

			String theColumnName = (String)theSetColumns.nextElement();
			String theColumnValue = insertEscapes(updateValues.getProperty(theColumnName));
			
			String actualType = "Text";
			if (updateTypes != null)
			{
				String updateTypeValue = updateTypes.getProperty(theColumnName);
				if (updateTypeValue != null)
				{
					actualType = updateTypeValue;
				}
			}
			actualType = actualType.toLowerCase();

			if (actualType.equals("numeric") == true)
			{
				theSetString += theColumnName + " = " + theColumnValue;
			}
			else
			{
				theSetString += theColumnName + " = '" + theColumnValue + "'";
			}
		}
	  
		theSQLStatement += theSetString;

		// Now, construct the WHERE part of the SQL statement
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
	  
		theSQLStatement += theWhereString;
		
		theSQLStatement += ";";
		
		Integer returnCode = executeSQLMutateStatement();
		
		if (returnCode != null)
		{
			return returnCode.intValue();
        }
		else
		{
			throw new InvalidReturnValueException("Invalid Return Code From Database"); 
		}
		
	}

    /**
     *
     * Returns a return code for the number of rows inserted, updated
     * or deleted in an Integer object
     *
     */
    //------------------------------------------------------------
    public Integer executeSQLMutateStatement()
    {
	   
		try
		{
			setUpDBConnection();
			Integer returnCode = (Integer)executeTheActualStatement();			
			
			return returnCode;
		}
		catch (SQLException sqle)
		{
		/* System.err.println( "An SQL Error Occured:" + sqle + "\n" +
            	sqle.getErrorCode() + "\n" + sqle.getMessage() + "\n"
			+ sqle); */
			//new Event(Event.GetLeafLevelClassName(this), "executeSQLMutateStatement", "SQL Exception: " + sqle.getErrorCode() + ": " + sqle.getMessage(), Event.ERROR);

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
			
			// The method executeQuery executes a insert/update/delete query on the database. 
			// The return value is the number of rows inserted, updated or deleted in
			// the database
			if (theSQLStatement != null)
			{
				int returnCode = theStatement.executeUpdate(theSQLStatement);
		
				return new Integer(returnCode);
			}
		}
		return null;
	}		
}

//---------------------------------------------------------------
//	Revision History:
//
//	$Log: DatabaseMutator.java,v $
//	Revision 1.4  2003/08/14 18:12:07  smitra
//	Updated to throw exception instead of return sentinel value
//	
//	Revision 1.3  2003/08/12 19:21:47  tomb
//	Updated format, added revision block.
//	
