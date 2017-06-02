package searchEntry;

import java.sql.*;
import javafx.application.Application;
import javafx.geometry.*; // for Insets, Pos
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*; // for Button, Label, TextField
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*; // for GridPane, HBox
import javafx.scene.text.*; // for Font, FontWeight, Text
import newEntry.*;

import application.Main;

public class searchData extends Application 
{
	@Override
	public void start(Stage searchPageStage) 
	{
		try
		{
			/* --- LAYOUT --- */
			//grid layout
			GridPane grid = new GridPane();
			grid.setAlignment(Pos.CENTER);
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(25, 25, 25, 25));
			//nodes
			Text sceneTitle = new Text("Enter Search Details : ");
			sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
			Label searchBy = new Label("SEARCH BY: ");
			final ToggleGroup searchSelection = new ToggleGroup();
			RadioButton cn = new RadioButton("Company Name");
			cn.setToggleGroup(searchSelection);
			RadioButton pos = new RadioButton("Position");
			pos.setToggleGroup(searchSelection);
			RadioButton city = new RadioButton("City");
			city.setToggleGroup(searchSelection);
			RadioButton state = new RadioButton("State");
			state.setToggleGroup(searchSelection);
			Label searchFor = new Label("Search For : ");
			TextField searchTextField = new TextField();
			//add nodes to layout
			grid.add(sceneTitle, 0, 0, 2, 1);
			grid.add(searchBy, 0, 1, 2, 1);
			grid.add(cn, 0, 2); grid.add(pos, 0, 3); grid.add(city, 0, 4); grid.add(state, 0, 5);
			grid.add(searchFor, 0, 6);
			grid.add(searchTextField, 1, 6);
			//dialog boxes for buttons
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setTitle("Error");
			errorAlert.setHeaderText(null);
			errorAlert.setContentText("All fields with * must be filled");
			//submit button
			Button searchBtn =  new Button("SEARCH");
			HBox searchHBtn = new HBox(10);
			searchHBtn.setAlignment(Pos.BOTTOM_RIGHT);
			searchHBtn.getChildren().add(searchBtn);
			grid.add(searchHBtn, 0, 7);
			searchBtn.setOnAction(event -> 
			{
				if (searchSelection.getSelectedToggle() == null || searchTextField.getText().isEmpty())
				{
					errorAlert.showAndWait();
				}
				else
				{
					errorAlert.showAndWait(); // direct to search results
					//clear all fields for next data entry
					searchTextField.clear();
					
				}
			});
			//cancel button
			Button btn =  new Button("CANCEL");
			HBox hBtn = new HBox(10);
			hBtn.setAlignment(Pos.CENTER);
			hBtn.getChildren().add(btn);
			grid.add(hBtn, 1, 7);
			btn.setOnAction(event ->
			{
				Main.getMainWindow(searchPageStage);
			});
			//final adding to layout
			Scene searchEntryScene = new Scene(grid, 400, 400);
			searchEntryScene.getStylesheets().add(dataEntry.class.getResource("dataEntryCSS.css").toExternalForm());
			searchPageStage.setTitle("Job Application Tracking System");
			searchPageStage.setScene(searchEntryScene);
			searchPageStage.show();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void getSearchWindow(Stage someStage)
	{
		searchData mainWindow = new searchData();
		mainWindow.start(someStage);
	}

	public static void main(String[] args) 
	{
		launch(args);
	}
}
