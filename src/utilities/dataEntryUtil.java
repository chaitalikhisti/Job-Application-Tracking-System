package utilities;

import java.sql.*; // for Connection, Statement
import java.time.LocalDate;
import javafx.scene.control.*; //for ComboBox, TextField

public class dataEntryUtil
{
	static Connection c = databaseConnection.establishConnection();
	static boolean dbConnFlag = databaseConnection.getConnFlag();
	static Statement st = null;
	static Statement st1 = null;
	static Statement st2 = null;
	static String compName, posName, cityName, stateName, commentDetails;
	static LocalDate date;
	static String refNo = null;
	static Boolean entryCondition = false;
	static Boolean entryState = false;
	
	//get values from data entry textfields
	public static void getParameters(TextField someCompTextField, TextField somePosTextField, TextField someRefTextField,
			TextField someCityTextField, ComboBox<String> someStateComboBox, DatePicker someDate, TextField someCommentTextField)
	{
		compName = someCompTextField.getText();
		posName = somePosTextField.getText();
		refNo = someRefTextField.getText();
		cityName = someCityTextField.getText();
		stateName = someStateComboBox.getValue();
		date = someDate.getValue();
		commentDetails = someCommentTextField.getText();
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
					"' AND `City` = '" +cityName+ "' AND `State` = '"+ stateName+ "')";
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
			//dbConnFlag = false;
			System.out.println("Caught Data Entry Check Exception");
			//e.printStackTrace();
		}		
		return entryCondition;
	}
	
	//enter data in database
	public static boolean enterData(TextField compTextField, TextField posTextField, TextField refNoTextField,
			TextField cityNameTextField, ComboBox<String> stateNameComboBox, DatePicker chooseDate, TextField comments)
	{
		getParameters(compTextField, posTextField, refNoTextField, cityNameTextField, stateNameComboBox, chooseDate, comments);
		int appNo = 0;
		if (dbConnFlag)
		{
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
						"(`App No`, `Date`, `Company`, `Position`, `City`, `State`, `Ref No`, `Comments`) " + 
						"VALUES ('" +appNo+ "', '" +date+ "', '" +compName+ "', '" +posName+
						"', '" +cityName+ "', '"+ stateName+ "', '" +refNo+ "', '" +commentDetails+ "')";
						st1.executeUpdate(str);
					}
					else
					{
						System.out.println("Check again");
					}
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
		}
		return entryState;
	}
	
	public static boolean getDBConnFlag()
	{
		return dbConnFlag;
	}
}
