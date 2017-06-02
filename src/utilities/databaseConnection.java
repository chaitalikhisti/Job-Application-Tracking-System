package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javafx.application.Application;
import javafx.stage.Stage;

public class databaseConnection extends Application 
{
	static Connection conn = null;
	static String dbURL, username, password;
	
	public static Connection establishConnection()
	{
		dbURL = "jdbc:mysql://dbURL";
	    username = "root";
	    password = "pass";
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
	
	//add unimplemented methods
	@Override
	public void start(Stage primaryStage) 
	{
		
	}
}
