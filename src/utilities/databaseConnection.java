package utilities;

import java.sql.*; // for Connection, DriverManager

public class databaseConnection
{
	static Connection conn = null;
	static String dbURL, username, password;
	
	public static Connection establishConnection()
	{
		dbURL = "jdbc:mysql://dbURL";
	    username = "root";
	    password = "password";
	    try
	    {
	    	conn = DriverManager.getConnection(dbURL, username, password); 
	        if (conn != null) 
	        {
	        	System.out.println("Connection Successfull!!!");
	        }
	        else
	        {
	        	System.out.println("Connection Establishment Failed");
	        }
	    }
	    catch (Exception e)
	    {
	    	e.printStackTrace();
	    }
	    return conn;
	}
}
