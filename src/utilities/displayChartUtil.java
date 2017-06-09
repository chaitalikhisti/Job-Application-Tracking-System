package utilities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Locale;

import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;

public class displayChartUtil 
{
	static Connection c = databaseConnection.establishConnection();
	static Statement st = null;
	static Statement st1 = null;
	static Statement st2 = null;
	static ResultSet rs;
	static int val;
	static String str; 
	
	public static int getValue(String someString)
	{
		 try
		 {
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
    
    public static Node weeklyChart(GridPane someGrid, ComboBox<String> someComboBox)
	{
    	final CategoryAxis weekXAxis = new CategoryAxis();
        final NumberAxis weekYAxis = new NumberAxis();
    	final BarChart<String,Number> weekBarChart = new BarChart<String,Number>(weekXAxis,weekYAxis); 
		//getting the week number
    	String weekName = someComboBox.getValue();
		//System.out.println(weekName);
		String regex = "Week ";
		weekName = weekName.replaceAll(regex, "");
		//System.out.println("edited week name: " +weekName);
		//String week = "1";
		int weekInt = Integer.parseInt(weekName);
		//getting date on sunday for that week
		LocalDate refDate = LocalDate.now();
		LocalDate refStartDate = LocalDate.of(refDate.getYear(), 01, 01);
		//LocalDate refStartDate = LocalDate.of(refDate.getYear(), 01, 01);
		//LocalDate refStartDate = LocalDate.of(2017, 06, 8);
		//LocalDate refEndDate = LocalDate.of(refDate.getYear(), 12, 31);
		TemporalField fieldUS = WeekFields.of(Locale.US).dayOfWeek();
		LocalDate refWeekStart = refStartDate.with(fieldUS, 1);
		//System.out.println("Nearest Sunday: " +refWeekStart);
		//working out 'for' logic
		LocalDate startDate = null, endDate = null;
		for (int i = 0; i < weekInt; i++)
		{
			startDate = refWeekStart;
			endDate = startDate.plus(Period.ofDays(6));
			refWeekStart = endDate.plus(Period.ofDays(1));
		}
		//System.out.println("Week " +weekInt+ ": " +startDate+ " to " +endDate);
		try
		{
			 st = c.createStatement();
		     XYChart.Series series = new XYChart.Series();
			 //calculating whole weeks dates
			 LocalDate today;
			 LocalDate yesterday;
			 String weekDay;
			 int getNoOfApps;
			 for (int i = 0; i < 7; i++)
			 {
				//get dates data
				 //today = LocalDate.now();
				 //yesterday = today.minus(Period.ofDays(i));
				 //yesterday = refWeekStart.plus(Period.ofDays(7));
				 //refWeekStart = yesterday;
				 //System.out.println("" +yesterday);
				 yesterday = startDate;
				 startDate = startDate.plus(Period.ofDays(1));
				 str = "SELECT COUNT(`App No`) FROM `jobdetails`.`jobdata` WHERE `Date` = '" +yesterday+ "'";
				 //System.out.println(str);
				 getNoOfApps = getValue(str);
				 weekDay = yesterday.getDayOfWeek().name();
				 String upToNCharacters = weekDay.substring(0, Math.min(weekDay.length(), 3));
				//construct barchart
				 series.getData().add(new XYChart.Data(upToNCharacters, getNoOfApps));
			 }
			 someGrid.getChildren().remove(someComboBox);
			 chartSetup(weekBarChart);
			 weekBarChart.setTitle("Applications in Week " +weekName);
			 weekBarChart.getData().add(series);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return weekBarChart;
	}
    
    public static Node monthlyChart(GridPane someGrid, ComboBox<String> someComboBox, int someMonthInt, String someMonthName)
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
			 //LocalDate today = LocalDate.of(refDate.getYear(), refDate.getMonthValue(), 01);
		     LocalDate today = LocalDate.of(refDate.getYear(), someMonthInt, 01);
		     int dayOfMonth, spanDates;
			 String dayOfMonthString;
			 int getNoOfApps;
			 for (int i = 0; i < refDate.lengthOfMonth(); i++)
			 {
				//get dates data
				 dayOfMonth = today.getDayOfMonth();
				 spanDates = dayOfMonth + i;
				 dayOfMonthString = "" +spanDates;
				 str = "SELECT COUNT(`App No`) FROM `jobdetails`.`jobdata` WHERE DAYOFMONTH(`Date`) = '" +spanDates+ "'";
				 getNoOfApps = getValue(str);
				 //construct barchart
				 series.getData().add(new XYChart.Data(dayOfMonthString, getNoOfApps));
			 }
			 someGrid.getChildren().remove(someComboBox);
			 chartSetup(monthBarChart);
			 monthBarChart.setTitle("Applications in " +someMonthName);
			 monthBarChart.getData().add(series);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}		
    	return monthBarChart;
	}
    
    public static Node yearlyChart(GridPane someGrid, ComboBox<String> someComboBox, int someYearInt)
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
				 str = "SELECT COUNT(`App No`) FROM `jobdetails`.`jobdata` WHERE MONTH(`Date`) = '" +spanMonths+ "'";
				 getNoOfApps = getValue(str);
				 monthName = spanMonthsName.name();
				 String smallMonthName = monthName.substring(0, Math.min(monthName.length(), 3));
				 //construct barchart
				 series.getData().add(new XYChart.Data(smallMonthName, getNoOfApps));
			 }
			 someGrid.getChildren().remove(someComboBox);
			 chartSetup(yearBarChart);
			 yearBarChart.setTitle("Applications in " +someYearInt);
			 yearBarChart.getData().add(series);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}		
		return yearBarChart;
    }
}
