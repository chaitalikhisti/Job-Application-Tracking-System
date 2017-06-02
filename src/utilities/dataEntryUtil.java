/* HAVE TO KEEP EITHER dataEntryUtil.java OR dataEntryUtilities.java */

package utilities;

import java.sql.*; // for Connection, Statement

import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;

public class dataEntryUtil 
{
	static Connection c = databaseConnection.establishConnection();
	static Statement st = null;
	static Statement st1 = null;
	//static int appNo = 1;
	static String compName, posName, refNo, cityName;
	static String stateName;
	
	public static void enterData(TextField compTextField, TextField posTextField, TextField refNoTextField,
			TextField cityNameTextField, ComboBox<String> stateNameComboBox)
	{
		compName = compTextField.getText();
		posName = posTextField.getText();
		refNo = refNoTextField.getText();
		cityName = cityNameTextField.getText();
		stateName = stateNameComboBox.getValue();
		int appNo = 0;
		try
		{
			st1 = c.createStatement();
			ResultSet rs = st1.executeQuery("SELECT COUNT(`Application No`) FROM `jobdetails`.`jobdata`");
			if (rs != null)
			{
				rs.next();
				appNo = rs.getInt(1);
				appNo++;
				st = c.createStatement();
				String str = "INSERT INTO `jobdetails`.`jobdata` " + 
				"(`Application No`, `Company Name`, `Position`, `City`, `State`, `Reference No`) " + 
				"VALUES ('" +appNo+ "', '" +compName+ "', '" +posName+ "', '" +cityName+ "', '"+ stateName+ "', '" +refNo+ "')";
				st.executeUpdate(str);
			}
			else
			{
				System.out.println("Check again");
			}
			c.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
