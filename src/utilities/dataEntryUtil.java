/* HAVE TO KEEP EITHER dataEntryUtil.java OR dataEntryUtilities.java */

package utilities;

import java.sql.*; // for Connection, Statement

public class dataEntryUtil 
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
}
