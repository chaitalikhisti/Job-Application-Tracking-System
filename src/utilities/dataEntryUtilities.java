/* HAVE TO KEEP EITHER dataEntryUtil.java OR dataEntryUtilities.java */

package utilities;

import javafx.application.Application;
import javafx.stage.Stage;
import java.sql.*;
import newEntry.dataEntry;

public class dataEntryUtilities extends Application
{
	static Statement st = null;
	
	public static void enterData(Connection c)
	{
		try
		{
			st = c.createStatement();			
			String str = "INSERT INTO `jobdetails`.`jobdata` (`Application No`, `Company Name`, `Position`, `City`, `State`, `Reference No`) VALUES ('App_No', 'Comp_Name', 'Pos_Name', 'City_Name', 'State_Name', '#Ref')";
			st.executeUpdate(str);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	// add unimplemented methods
	@Override
	public void start(Stage arg0) throws Exception 
	{
		// TODO Auto-generated method stub		
	}
}
