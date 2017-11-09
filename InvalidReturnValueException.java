// tabs=4
//************************************************************
//	COPYRIGHT ArchSynergy, Ltd. 2003 - ALL RIGHTS RESERVED
//
// This file is the product of ArchSynergy, Ltd. and is 
// provided for unrestricted use provided that this legend 
// is included on all tape media and as part of the software 
// program in whole or part. Users may copy or modify this 
// file without charge, but are not authorized to license or 
// distribute it to anyone else except as part of a product or 
// program developed by the user.
//*************************************************************
//
//	FILE NAME:		$RCSfile: InvalidReturnValueException.java,v $       
//
//	Project:		$ProjectName:  $
//
//	LocalVersion:	$Revision: 1.1 $
//
//	ArchiveVersion:	$ProjectRevision:  $
//
//	Date & Time:	$Date: 2003/08/14 18:11:37 $
//
//	Revisions: Please see end of file
//
//**************************************************************

/** @author		$Author: smitra $ */
/** @version	$Revision: 1.1 $ */

// specify the package

// system imports

// local imports

/** 
 * This class indicates an exception that is thrown if the primary
 * key is not properly supplied to the data access model object as
 * it seeks to retrieve a record from the database
 * 
 */
//--------------------------------------------------------------
public class InvalidReturnValueException
	extends Exception
{	
	/**
	 * Constructor with message
	 *
	 * @param mesg The message associated with the exception
	 */
	//--------------------------------------------------------
	public InvalidReturnValueException(String message)
	{
		super(message);
	}
}

		

//**************************************************************
//	Revision History:
//
//	$Log: InvalidReturnValueException.java,v $
//	Revision 1.1  2003/08/14 18:11:37  smitra
//	First check-in
//	

//	