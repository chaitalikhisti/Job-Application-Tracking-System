package utilities;

import java.sql.*; // for Connection, Statement
import java.time.LocalDate;

import javafx.scene.control.*; //for ComboBox, TextField

public class dataEntryUtil
{
	static Connection c = databaseConnection.establishConnection();
	static Statement st = null;
	static Statement st1 = null;
	static Statement st2 = null;
	static String compName, posName, cityName, stateName;
	static LocalDate date;
	static String refNo = null;
	static Boolean entryCondition, entryState;
	
	//get values from data entry textfields
	public static void getParameters(TextField someCompTextField, TextField somePosTextField, TextField someRefTextField,
			TextField someCityTextField, ComboBox<String> someStateComboBox, DatePicker someDate)
	{
		compName = someCompTextField.getText();
		posName = somePosTextField.getText();
		refNo = someRefTextField.getText();
		cityName = someCityTextField.getText();
		stateName = someStateComboBox.getValue();
		date = someDate.getValue();
	}
	
	//check if record already exists
	public static boolean entryCheck(String someCompName, String somePosName, String someRefNo,
				String someCityName, String someStateName)
	{
		try
		{
			st2 = c.createStatement();
			String searchString = "SELECT * FROM `jobdetails`.`jobdata` WHERE " + 
					"(`Company` = '" +compName+ "' AND `Position` = '" +posName+ 
					"' AND `City` = '" +cityName+ "' AND `State` = '"+ stateName+ "' AND `Ref No` = '" +refNo+ "')";
			ResultSet rs = st2.executeQuery(searchString);
			if (rs.next())
			{
				entryCondition = false;
			}
			else
			{
				entryCondition = true;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return entryCondition;
	}
	
	//enter data in database
	public static boolean enterData(TextField compTextField, TextField posTextField, TextField refNoTextField,
			TextField cityNameTextField, ComboBox<String> stateNameComboBox, DatePicker chooseDate)
	{
		getParameters(compTextField, posTextField, refNoTextField, cityNameTextField, stateNameComboBox, chooseDate);
		int appNo = 0;
		if (entryCheck(compName, posName, refNo, cityName, stateName))
		{
			try
			{
				st = c.createStatement();
				ResultSet rs = st.executeQuery("SELECT COUNT(`App No`) FROM `jobdetails`.`jobdata`");
				if (rs != null)
				{
					rs.next();
					appNo = rs.getInt(1);
					appNo++;
					st1 = c.createStatement();
					String str = "INSERT INTO `jobdetails`.`jobdata` " + 
					"(`App No`, `Date`, `Company`, `Position`, `City`, `State`, `Ref No`) " + 
					"VALUES ('" +appNo+ "', '" +date+ "', '" +compName+ "', '" +posName+ "', '" +cityName+ "', '"+ stateName+ "', '" +refNo+ "')";
					st1.executeUpdate(str);
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
			entryState = true;
		}
		else
		{
			entryState = false;
		}
		return entryState;
	}
}
