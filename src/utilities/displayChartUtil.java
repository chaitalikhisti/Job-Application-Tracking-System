package utilities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;

import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class displayChartUtil 
{
	static Connection c = databaseConnection.establishConnection();
	static Statement st = null;
	static Statement st1 = null;
	static Statement st2 = null;
	static ResultSet rs;
	static int val;
	static String str;
//	final static CategoryAxis xAxis = new CategoryAxis();
//    final static NumberAxis yAxis = new NumberAxis();
    //final static BarChart<String,Number> barChart = new BarChart<String,Number>(xAxis,yAxis); 
	
	public static int getValue(String someString)
	{
		 try
		 {
			 //str = "SELECT COUNT(`App No`) FROM `jobdetails`.`jobdatatrial` WHERE `Date` = '" +someLocalDate+ "'";
			 rs = st.executeQuery(someString);
			 rs.next();
			 val = rs.getInt(1);
		 }
		 catch (Exception e)
		 {
			 e.printStackTrace();
		 }
		 return val;
	}
	
	public static void chartSetup(BarChart<String,Number> someChart)
	{
		someChart.setMinHeight(275);
		someChart.setLegendVisible(false);
		someChart.setCategoryGap(3);
	}
    
    public static Node weeklyChart()
	{
    	final CategoryAxis weekXAxis = new CategoryAxis();
        final NumberAxis weekYAxis = new NumberAxis();
    	final BarChart<String,Number> weekBarChart = new BarChart<String,Number>(weekXAxis,weekYAxis); 
    	try
		{
			 st = c.createStatement();
		     XYChart.Series series = new XYChart.Series();
			 //calculating whole weeks dates
			 LocalDate today;
			 LocalDate yesterday;
			 String weekDay, weekDay2;
			 int getNoOfApps;
			 for (int i = 0; i < 7; i++)
			 {
				//get dates data
				 today = LocalDate.now();
				 yesterday = today.minus(Period.ofDays(i));
				 str = "SELECT COUNT(`App No`) FROM `jobdetails`.`jobdatatrial` WHERE `Date` = '" +yesterday+ "'";
				 getNoOfApps = getValue(str);
				 weekDay = yesterday.getDayOfWeek().name();
				 String upToNCharacters = weekDay.substring(0, Math.min(weekDay.length(), 3));
				//construct barchart
				 series.getData().add(new XYChart.Data(upToNCharacters, getNoOfApps));
			 }
			 chartSetup(weekBarChart);
			 weekBarChart.getData().add(series);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return weekBarChart;
	}
    
    public static Node monthlyChart()
	{
    	final CategoryAxis monthXAxis = new CategoryAxis();
        final NumberAxis monthYAxis = new NumberAxis();
    	final BarChart<String,Number> monthBarChart = new BarChart<String,Number>(monthXAxis,monthYAxis); 
    	try
		{
			 st = c.createStatement();
		     XYChart.Series series = new XYChart.Series();
			 //calculating whole weeks dates
			 LocalDate refDate = LocalDate.now();
			 LocalDate today = LocalDate.of(refDate.getYear(), refDate.getMonthValue(), 01);
			 int dayOfMonth, spanDates;
			 String dayOfMonthString;
			 int getNoOfApps;
			 for (int i = 0; i < refDate.lengthOfMonth(); i++)
			 {
				//get dates data
				 dayOfMonth = today.getDayOfMonth();
				 spanDates = dayOfMonth + i;
				 dayOfMonthString = "" +spanDates;
				 str = "SELECT COUNT(`App No`) FROM `jobdetails`.`jobdatatrial` WHERE DAYOFMONTH(`Date`) = '" +spanDates+ "'";
				 getNoOfApps = getValue(str);
				 //construct barchart
				 series.getData().add(new XYChart.Data(dayOfMonthString, getNoOfApps));
			 }
			 chartSetup(monthBarChart);
			 monthBarChart.getData().add(series);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}		
    	return monthBarChart;
	}
    
    public static Node yearlyChart()
    {
    	final CategoryAxis yearXAxis = new CategoryAxis();
        final NumberAxis yearYAxis = new NumberAxis();
    	final BarChart<String,Number> yearBarChart = new BarChart<String,Number>(yearXAxis,yearYAxis); 
    	try
		{
			 st = c.createStatement();
			 //set basic barchart features
		     XYChart.Series series = new XYChart.Series();
			 //calculating whole weeks dates
		     LocalDate refDate = LocalDate.now();
			 LocalDate today = LocalDate.of(refDate.getYear(), 01, 01);
			 int currentMonth, spanMonths;
			 Month currentMonthName, spanMonthsName;
			 String monthName;
			 int getNoOfApps;
			 for (int i = 0; i < 12; i++)
			 {
				//get dates data
				 currentMonth = today.getMonthValue();
				 currentMonthName = today.getMonth();
				 spanMonths = currentMonth + i;
				 spanMonthsName = currentMonthName.plus(i);
				 str = "SELECT COUNT(`App No`) FROM `jobdetails`.`jobdatatrial` WHERE MONTH(`Date`) = '" +spanMonths+ "'";
				 getNoOfApps = getValue(str);
				 monthName = spanMonthsName.name();
				 String smallMonthName = monthName.substring(0, Math.min(monthName.length(), 3));
				 //construct barchart
				 series.getData().add(new XYChart.Data(smallMonthName, getNoOfApps));
			 }
			 chartSetup(yearBarChart);
			 yearBarChart.getData().add(series);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}		
		return yearBarChart;
    }
}
