package utilities;

import javafx.scene.Node;
import javafx.scene.chart.*; // for barchart, categpryaxis, numberaxis, XYchart
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import java.sql.*; // for connection, resultset, statement
import java.time.*; // for localdate, month, period
import java.time.temporal.*; // for temporalfield, weekfields
import java.util.Locale;

public class displayChartUtil 
{
	static Connection c = databaseConnection.establishConnection();
	static boolean dbConnFlag = databaseConnection.getConnFlag();
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
		someChart.setMaxHeight(300);
		someChart.setLegendVisible(false);
		someChart.setCategoryGap(3);
	}
    
    public static Node weeklyChart(GridPane someGrid, ComboBox<String> someComboBox)
	{
    	//variables and components required for the chart
    	final CategoryAxis weekXAxis = new CategoryAxis();
        final NumberAxis weekYAxis = new NumberAxis();
    	final BarChart<String,Number> weekBarChart = new BarChart<String,Number>(weekXAxis,weekYAxis); 
    	int weekInt = someComboBox.getSelectionModel().getSelectedIndex() + 1;
    	LocalDate refDate, refStartDate, refWeekStart, yesterday;
    	LocalDate startDate = null, endDate = null, forLoopStartDate = null;
    	Month currentMonth;
		String weekName, regex, smallStartWeekDay, startDateDetails, smallEndWeekDay;
		String endDateDetails, weekDay, monthName, smallMonthName, dayAndDate;
		int getNoOfApps, totalApps = 0, currentDate;
		//getting the week number and start-end dates
    	refDate = LocalDate.now();
		refStartDate = LocalDate.of(refDate.getYear(), 01, 01);
		TemporalField fieldUS = WeekFields.of(Locale.US).dayOfWeek();
		refWeekStart = refStartDate.with(fieldUS, 1);
		//acquiring start and end dates for the selected week
		for (int i = 0; i < weekInt; i++)
		{
			startDate = refWeekStart;
			endDate = startDate.plus(Period.ofDays(6));
			refWeekStart = endDate.plus(Period.ofDays(1));
		}
		smallStartWeekDay = startDate.getMonth().name().substring(0, Math.min(startDate.getMonth().name().length(), 3));
		startDateDetails = startDate.getDayOfMonth()+ " " +smallStartWeekDay+ " " +startDate.getYear();
		smallEndWeekDay = endDate.getMonth().name().substring(0, Math.min(endDate.getMonth().name().length(), 3));
		endDateDetails = endDate.getDayOfMonth()+ " " +smallEndWeekDay+ " " +endDate.getYear();		
		forLoopStartDate = startDate;
		if (dbConnFlag)
		{
			try
			{
				 st = c.createStatement();
			     XYChart.Series series = new XYChart.Series();
				 //calculations and display in chart
				 for (int i = 0; i < 7; i++)
				 {
					//get data
					 yesterday = forLoopStartDate;
					 str = "SELECT COUNT(`App No`) FROM `jobdetails`.`jobdata` WHERE `Date` = '" +yesterday+ "'";
					 getNoOfApps = getValue(str);
					 totalApps += getNoOfApps;
					 //x-axis labels
					 weekDay = yesterday.getDayOfWeek().name();
					 String dayOfWeek = weekDay.substring(0, Math.min(weekDay.length(), 3));
					 currentMonth = yesterday.getMonth();
					 monthName = currentMonth.name();
					 smallMonthName = monthName.substring(0, Math.min(monthName.length(), 3));
					 currentDate = yesterday.getDayOfMonth();
					 dayAndDate = "   " +dayOfWeek+ "\n(" + smallMonthName+"-" +currentDate+ ")";	
					 //construct barchart
					 series.getData().add(new XYChart.Data(dayAndDate, getNoOfApps));
					 forLoopStartDate = forLoopStartDate.plus(Period.ofDays(1));
				 }
				 someGrid.getChildren().remove(someComboBox);
				 chartSetup(weekBarChart);
				 weekBarChart.setTitle("Applications in Week " +weekInt+ " (" +startDateDetails+ " to " +endDateDetails+ ") : " +totalApps);
				 weekBarChart.getData().add(series);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return weekBarChart;
	}
    
    public static Node monthlyChart(GridPane someGrid, ComboBox<String> someComboBox, int someMonthInt, String someMonthName)
	{
    	//variables and components required for the chart
    	final CategoryAxis monthXAxis = new CategoryAxis();
        final NumberAxis monthYAxis = new NumberAxis();
    	final BarChart<String,Number> monthBarChart = new BarChart<String,Number>(monthXAxis,monthYAxis); 
    	LocalDate refDate, today;
    	Month currentMonthName;
		String dayOfMonthString;
    	int dayOfMonth, currentMonth, spanDates, getNoOfApps, totalApps = 0;
    	//acquiring start date for selected month in current year
	    refDate = LocalDate.now();
	    today = LocalDate.of(refDate.getYear(), someMonthInt, 01);
    	if (dbConnFlag)
    	{
    		try
    		{
    			 st = c.createStatement();
    		     XYChart.Series series = new XYChart.Series();
    			//calculations and display in chart
    			 for (int i = 0; i < refDate.lengthOfMonth(); i++)
    			 {
    				//get dates data
    				 dayOfMonth = today.getDayOfMonth();
    				 spanDates = dayOfMonth + i;
    				 dayOfMonthString = "" +spanDates;
    				 currentMonth = today.getMonthValue();
    				 str = "SELECT COUNT(`App No`) FROM `jobdetails`.`jobdata` WHERE DAYOFMONTH(`Date`) = '"
    				 +spanDates+ "' AND MONTH(`Date`) = '" +currentMonth+ "'";
    				 getNoOfApps = getValue(str);
    				 totalApps += getNoOfApps;
    				 currentMonthName = today.getMonth();
    				 //construct barchart
    				 series.getData().add(new XYChart.Data(dayOfMonthString, getNoOfApps));
    			 }
    			 someGrid.getChildren().remove(someComboBox);
    			 chartSetup(monthBarChart);
    			 monthBarChart.setTitle("Applications in " +someMonthName+ " : " +totalApps);
    			 monthBarChart.getData().add(series);
    		}
    		catch (Exception e)
    		{
    			e.printStackTrace();
    		}
    	}
    	return monthBarChart;
	}
    
    public static Node yearlyChart(GridPane someGrid, ComboBox<String> someComboBox, int someYearInt)
    {
    	//variables and components required for the chart
    	final CategoryAxis yearXAxis = new CategoryAxis();
        final NumberAxis yearYAxis = new NumberAxis();
    	final BarChart<String,Number> yearBarChart = new BarChart<String,Number>(yearXAxis,yearYAxis); 
    	LocalDate refDate, today;
    	int currentMonth, spanMonths, getNoOfApps, totalApps = 0;
		Month currentMonthName, spanMonthsName;
		String monthName;
		//acquiring first day of current year
	    //refDate = LocalDate.now();
		today = LocalDate.of(someYearInt, 01, 01);
    	if (dbConnFlag)
    	{
    		try
    		{
    			 st = c.createStatement();
    		     XYChart.Series series = new XYChart.Series();
    		     //calculations and display in chart
    			 for (int i = 0; i < 12; i++)
    			 {
    				 //get dates data
    				 currentMonth = today.getMonthValue();
    				 currentMonthName = today.getMonth();
    				 spanMonths = currentMonth + i;
    				 spanMonthsName = currentMonthName.plus(i);
    				 str = "SELECT COUNT(`App No`) FROM `jobdetails`.`jobdata` WHERE MONTH(`Date`) = '" +spanMonths+ "'"
    				 		+ " AND YEAR(`Date`) = '" +someYearInt+ "'";
    				 getNoOfApps = getValue(str);
    				 totalApps += getNoOfApps;
    				 monthName = spanMonthsName.name();
    				 String smallMonthName = monthName.substring(0, Math.min(monthName.length(), 3));
    				 //construct barchart
    				 series.getData().add(new XYChart.Data(smallMonthName, getNoOfApps));
    			 }
    			 someGrid.getChildren().remove(someComboBox);
    			 chartSetup(yearBarChart);
    			 yearBarChart.setTitle("Applications in " +someYearInt+ " : " +totalApps);
    			 yearBarChart.getData().add(series);
    		}
    		catch (Exception e)
    		{
    			e.printStackTrace();
    		}	
    	}
		return yearBarChart;
    }
    
    public static boolean getDBConnFlag()
	{
		return dbConnFlag;
	}
}
