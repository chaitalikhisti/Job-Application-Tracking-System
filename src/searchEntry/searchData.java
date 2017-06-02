package searchEntry;

import javafx.application.Application;
import javafx.geometry.*; // for Insets, Pos
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*; // for Button, Label, TextField
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*; // for GridPane, HBox
import javafx.scene.text.*; // for Font, FontWeight, Text
import newEntry.*;
import utilities.*; //for getWindows, searchEntryUtil

public class searchData extends Application 
{
	@Override
	public void start(Stage searchPageStage) 
	{
		//declaring all components
		GridPane grid;
		Text sceneTitle;
		Label searchBy, searchFor;
		final ToggleGroup searchSelection = new ToggleGroup();
		RadioButton cn, pos, city, state, refNo;
		TextField searchTextField;
		Alert errorAlert;
		Button searchBtn, btn;
		HBox searchHBtn, hBtn;
		Scene searchEntryScene;
		
		try
		{
			//grid layout
			grid = new GridPane();
			grid.setAlignment(Pos.CENTER);
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(25, 25, 25, 25));
			//nodes
			sceneTitle = new Text("Enter Search Details : ");
			sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
			searchBy = new Label("SEARCH BY: ");
			cn = new RadioButton("Company Name");
			cn.setToggleGroup(searchSelection);
			pos = new RadioButton("Position");
			pos.setToggleGroup(searchSelection);
			city = new RadioButton("City");
			city.setToggleGroup(searchSelection);
			state = new RadioButton("State");
			state.setToggleGroup(searchSelection);
			refNo = new RadioButton("Reference No.");
			refNo.setToggleGroup(searchSelection);
			searchFor = new Label("Search For : ");
			searchTextField = new TextField();
			//add nodes to layout
			grid.add(sceneTitle, 0, 0, 2, 1);
			grid.add(searchBy, 0, 1, 2, 1);
			grid.add(cn, 0, 2); grid.add(pos, 0, 3); grid.add(city, 0, 4); grid.add(state, 0, 5); grid.add(refNo, 0, 6);
			grid.add(searchFor, 0, 7);
			grid.add(searchTextField, 1, 7);
			//dialog boxes for buttons
			errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setTitle("Error");
			errorAlert.setHeaderText(null);
			errorAlert.setContentText("All fields with * must be filled");
			//submit button
			searchBtn =  new Button("SEARCH");
			searchHBtn = new HBox(10);
			searchHBtn.setAlignment(Pos.BOTTOM_RIGHT);
			searchHBtn.getChildren().add(searchBtn);
			grid.add(searchHBtn, 0, 8);
			searchBtn.setOnAction(event -> 
			{
				if (searchSelection.getSelectedToggle() == null || searchTextField.getText().isEmpty())
				{
					errorAlert.showAndWait();
				}
				else
				{
					//getting toggle selection
					String radioSelectionString = "";
					RadioButton selectedRadioButton = (RadioButton) searchSelection.getSelectedToggle();
					if (selectedRadioButton == cn)
					{
						radioSelectionString = "Company Name";
					}
					else if (selectedRadioButton == pos)
					{
						radioSelectionString = "Position";
					}
					else if (selectedRadioButton == city)
					{
						radioSelectionString = "City";
					}
					else if (selectedRadioButton == state)
					{
						radioSelectionString = "State";
					}
					else if (selectedRadioButton == refNo)
					{
						radioSelectionString = "Ref No";
					}
					else
					{
						errorAlert.showAndWait();
					}
					//search data in database
					getWindows.getSearchResultsWindow(searchPageStage,radioSelectionString, searchTextField);
					//clear all fields for next data entry
					searchTextField.clear();					
				}
			});
			//cancel button
			btn =  new Button("CANCEL");
			hBtn = new HBox(10);
			hBtn.setAlignment(Pos.CENTER);
			hBtn.getChildren().add(btn);
			grid.add(hBtn, 1, 8);
			btn.setOnAction(event ->
			{
				getWindows.getMainWindow(searchPageStage);
			});
			//final adding to layout
			searchEntryScene = new Scene(grid, 400, 400);
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
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}
