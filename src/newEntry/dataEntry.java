package newEntry;

import javafx.application.Application;
import javafx.geometry.*; // for Insets, Pos
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*; // for Button, Label, TextField
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*; // for GridPane, HBox
import javafx.scene.text.*; // for Font, FontWeight, Text
import utilities.*;

public class dataEntry extends Application 
{
	//declaring all components
	GridPane grid;
	Text sceneTitle;
	Label compName, posName, refNo, cityName, stateName;
	TextField compTextField, posTextField, refNoTextField, cityNameTextField;
	final ComboBox<String> stateNameComboBox = new ComboBox<String>();
	Alert errorAlert, submitAlert;
	Button submitBtn, cancelBtn;
	HBox submitHBtn, cancelHBtn;
	Scene dataEntryScene;
	
	@Override
	public void start(Stage logPageStage) 
	{
		try 
		{
			/* --- LAYOUT --- */
			//grid layout
			grid = new GridPane();
			grid.setAlignment(Pos.CENTER);
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(25, 25, 25, 25));
			//nodes
			sceneTitle = new Text("Enter Application Details : ");
			sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
			compName = new Label("Company Name *: ");
			compTextField = new TextField();
			posName = new Label("Position *: ");
			posTextField = new TextField();
			refNo = new Label("Reference No. : ");
			refNoTextField = new TextField();			
			cityName = new Label("City *: ");
			cityNameTextField = new TextField();
			stateName = new Label("State *: ");
			stateNameComboBox.getItems().addAll
			(
				"AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA",
				"HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD",
				"MA", "MI", "MN", "MS", "MS", "MO", "MT", "NE", "NV", "NH",
				"NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI",
				"SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI",
				"WY" 
	        ); 
			stateNameComboBox.setValue("Select State");
			//add nodes to layout
			grid.add(sceneTitle, 0, 0, 2, 1);
			grid.add(compName, 0, 1);
			grid.add(compTextField, 1, 1);
			grid.add(posName, 0, 2);
			grid.add(posTextField, 1, 2);
			grid.add(refNo, 0, 3);
			grid.add(refNoTextField, 1, 3);
			grid.add(cityName, 0, 4);
			grid.add(cityNameTextField, 1, 4);
			grid.add(stateName, 0, 5);
			grid.add(stateNameComboBox, 1, 5);
			grid.setGridLinesVisible(false);
			//dialog boxes for buttons
			errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setTitle("Error");
			errorAlert.setHeaderText(null);
			errorAlert.setContentText("All fields with * must be filled");
			submitAlert = new Alert(AlertType.CONFIRMATION);
			submitAlert.setTitle("Successful!!!");
			submitAlert.setHeaderText(null);
			submitAlert.setContentText("Data successfully updated");
			//submit button
			submitBtn =  new Button("SUBMIT");
			submitHBtn = new HBox(10);
			submitHBtn.setAlignment(Pos.BOTTOM_RIGHT);
			submitHBtn.getChildren().add(submitBtn);
			grid.add(submitHBtn, 0, 6);
			submitBtn.setOnAction(event -> 
			{
				if (compTextField.getText().isEmpty() || posTextField.getText().isEmpty() || cityNameTextField.getText().isEmpty() || stateNameComboBox.getSelectionModel().isEmpty())
				{
					errorAlert.showAndWait();
				}
				else
				{
					submitAlert.showAndWait();
					//enter data in database
					dataEntryUtil.enterData(compTextField, posTextField, refNoTextField, cityNameTextField, stateNameComboBox);
					//clear all fields for next data entry
					compTextField.clear();
					posTextField.clear();
					refNoTextField.clear();
					cityNameTextField.clear();
					stateNameComboBox.setValue("Select State");
				}
			});
			//cancel button
			cancelBtn =  new Button("CANCEL");
			cancelHBtn = new HBox(10);
			cancelHBtn.setAlignment(Pos.CENTER);
			cancelHBtn.getChildren().add(cancelBtn);
			grid.add(cancelHBtn, 1, 6);
			cancelBtn.setOnAction(event ->
			{
				getWindows.getMainWindow(logPageStage);
			});
			//final adding to layout
			dataEntryScene = new Scene(grid, 400, 400);
			dataEntryScene.getStylesheets().add(dataEntry.class.getResource("dataEntryCSS.css").toExternalForm());
			logPageStage.setTitle("Job Application Tracking System");
			logPageStage.setScene(dataEntryScene);
			logPageStage.show();			
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
}
