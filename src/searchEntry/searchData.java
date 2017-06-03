package searchEntry;

import application.Main;
import javafx.application.Application;
import javafx.geometry.*; // for Insets, Pos
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*; // for Button, Label, TextField
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
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
		Text sceneTitle, invisibleText;
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
			grid.setAlignment(Pos.TOP_CENTER);
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(25, 25, 25, 25));
			ColumnConstraints col1 = new ColumnConstraints();
		    col1.setPercentWidth(30);
		    ColumnConstraints col2 = new ColumnConstraints();
		    col2.setPercentWidth(50);
			//nodes
			sceneTitle = new Text("               Data Search Details");
			sceneTitle.setId("dataEntrySceneTitle");
			invisibleText = new Text("");
			invisibleText.setId("invisibleText");
			searchBy = new Label("SEARCH BY: ");
			cn = new RadioButton("Company Name");
			cn.setToggleGroup(searchSelection);
			pos = new RadioButton("Position");
			pos.setToggleGroup(searchSelection);
			city = new RadioButton("City");
			city.setToggleGroup(searchSelection);
			state = new RadioButton("State");
			state.setToggleGroup(searchSelection);
			refNo = new RadioButton("Application No.");
			refNo.setToggleGroup(searchSelection);
			searchFor = new Label("SEARCH FOR : ");
			searchTextField = new TextField();
			//add nodes to layout
			grid.getColumnConstraints().addAll(col1,col2);
			grid.add(sceneTitle, 0, 0, 2, 1);
			grid.add(invisibleText, 0, 1, 2, 1);
			grid.add(searchBy, 0, 2, 2, 1);
			grid.add(cn, 0, 3); grid.add(pos, 0, 4); grid.add(city, 0, 5); grid.add(state, 0, 6); grid.add(refNo, 0, 7);
			grid.add(searchFor, 0, 8);
			grid.add(searchTextField, 1, 8);
			grid.setGridLinesVisible(false);
			//dialog boxes for buttons
			errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setTitle("Error");
			errorAlert.setHeaderText(null);
			errorAlert.setContentText("All fields with * must be filled");
			//submit button
			searchBtn =  new Button("SEARCH");
			searchHBtn = new HBox(10);
			searchHBtn.setId("searchDataHBtn");
			searchHBtn.setAlignment(Pos.BOTTOM_RIGHT);
			searchHBtn.getChildren().add(searchBtn);
			grid.add(searchHBtn, 0, 9);
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
					searchSelection.selectToggle(null);
				}
			});
			//cancel button
			btn =  new Button("CANCEL");
			hBtn = new HBox(10);
			hBtn.setId("searchDataCancelHBtn");
			hBtn.setAlignment(Pos.CENTER);
			hBtn.getChildren().add(btn);
			grid.add(hBtn, 1, 9);
			btn.setOnAction(event ->
			{
				getWindows.getMainWindow(searchPageStage);
			});
			//final adding to layout
			searchEntryScene = new Scene(grid, 800, 600);
			//searchEntryScene.getStylesheets().add(dataEntry.class.getResource("dataEntryCSS.css").toExternalForm());
			searchEntryScene.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
			searchPageStage.setTitle("Job Application Tracking System");
			searchPageStage.getIcons().add(new Image("file:hireme.png"));
			searchPageStage.setScene(searchEntryScene);
			searchPageStage.show();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
