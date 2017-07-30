package utilities;

import java.sql.*; // for Connection, DriverManager

public class databaseConnection
{
	static Connection conn = null;
	public static boolean connFlag = false; //flag to test connection success
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
	        	connFlag = true;
	        	//System.out.println("Connection Successfull!!!");
	        }
	        else
	        {
	        	connFlag = false;
	        	//System.out.println("Connection Establishment Failed");
	        }
	    }
	    catch (SQLException e)
	    {
	    	connFlag = false;
	    	//System.out.println("Connection Establishment Failed");
	    	//e.printStackTrace();
	    }
	    return conn;
	}
	
	public static boolean getConnFlag()
	{
		return connFlag;
	}
	
	public static void closeConnection() throws SQLException
	{
		conn.close();
	}
}
