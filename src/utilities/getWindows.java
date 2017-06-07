package utilities;

import application.Main;
import javafx.application.Application;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
	public static void getSearchResultsWindow(Stage someStage, String someString, TextField someTextField, DatePicker someDatePicker)
	{
		displayEntries mainWindow = new displayEntries();
		mainWindow.start(someStage, someString, someTextField, someDatePicker);
	}
	
	//add unimplemented method
	@Override
	public void start(Stage primaryStage) 
	{
		
	}
}
