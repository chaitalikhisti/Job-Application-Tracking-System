package utilities;

import application.Main;
import displayCharts.dispChart;
import javafx.application.Application;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import newEntry.dataEntry;
import searchEntry.searchData;
import searchDisplay.displayEntries;

public class getWindows extends Application 
{
	//method to load Main Window Stage
	public static void getMainWindow(Stage someStage)
	{
		Main mainWindow = new Main();
		mainWindow.start(someStage);
	}
	
	//method to load Data Entry Window Stage
	public static void getDataEntryWindow(Stage someStage)
	{
		dataEntry mainWindow = new dataEntry();
		mainWindow.start(someStage);
	}
	
	//method to load Data Search Window Stage
	public static void getSearchWindow(Stage someStage)
	{
		searchData mainWindow = new searchData();
		mainWindow.start(someStage);
	}
	
	//method to load Search Results Stage
	public static void getSearchResultsWindow(Stage someStage, String someString, TextField someTextField)
	{
		displayEntries mainWindow = new displayEntries();
		mainWindow.start(someStage, someString, someTextField);
	}
	
	public static void getSearchResultsWindow(Stage someStage) 
	{
		displayEntries mainWindow = new displayEntries();
		mainWindow.alternativeStart(someStage);	
	}
	
	//method to load Statistical Analysis Stage
	public static void getStatsResultsWindow(Stage someStage)
	{
		dispChart mainWindow = new dispChart();
		mainWindow.start(someStage);
	}
	
	//unimplemented method
	@Override
	public void start(Stage primaryStage) 
	{
		
	}
}
