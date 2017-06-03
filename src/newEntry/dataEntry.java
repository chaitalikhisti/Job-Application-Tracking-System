package newEntry;

import application.Main;
import javafx.application.Application;
import javafx.geometry.*; // for Insets, Pos
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*; // for Button, Label, TextField
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.*; // for GridPane, HBox
import javafx.scene.text.*; // for Text
import utilities.*;

public class dataEntry extends Application 
{
	//declaring all components
	GridPane grid;
	Text sceneTitle, invisibleText;
	Label compName, posName, refNo, cityName, stateName, colonText1, colonText2, colonText3, colonText4, colonText5;
	TextField compTextField, posTextField, refNoTextField, cityNameTextField;
	String asterisk, colon;
	final ComboBox<String> stateNameComboBox = new ComboBox<String>();
	Alert errorAlert, submitAlert;
	Button submitBtn, cancelBtn;
	HBox submitHBtn, cancelHBtn;
	Scene dataEntryScene;
	
	@Override
	public void start(Stage logPageStage) 
	{
		asterisk = "*";
		colon = ":";
		try 
		{
			/* --- LAYOUT --- */
			//grid layout
			grid = new GridPane();
			grid.setAlignment(Pos.TOP_CENTER);
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(25, 25, 25, 25));
			ColumnConstraints col1 = new ColumnConstraints();
		    col1.setPercentWidth(25);
		    ColumnConstraints col2 = new ColumnConstraints();
		    col2.setPercentWidth(5);
		    ColumnConstraints col3 = new ColumnConstraints();
		    col3.setPercentWidth(50);
			//nodes
			sceneTitle = new Text("               Application Details");
			sceneTitle.setId("dataEntrySceneTitle");
			invisibleText = new Text("");
			invisibleText.setId("invisibleText");
			compName = new Label("Company Name");
			colonText1 = new Label(asterisk+colon);
			compTextField = new TextField();
			compTextField.setPrefSize(300, 35);
			posName = new Label("Position");
			colonText2 = new Label(asterisk+colon);
			posTextField = new TextField();
			posTextField.setPrefSize(300, 35);
			refNo = new Label("Application ID");
			colonText3 = new Label("  " +colon);
			refNoTextField = new TextField();	
			refNoTextField.setPrefSize(300, 35);
			cityName = new Label("City");
			colonText4 = new Label(asterisk+colon);
			cityNameTextField = new TextField();
			cityNameTextField.setPrefSize(300, 35);
			stateName = new Label("State");
			colonText5 = new Label(asterisk+colon);
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
			stateNameComboBox.setPrefSize(400, 35);
			//add nodes to layout
			grid.getColumnConstraints().addAll(col1,col2,col3);
			grid.add(sceneTitle, 0, 0, 2, 1);
			grid.add(invisibleText, 0, 1, 2, 1);
			grid.add(compName, 0, 2);
			grid.add(colonText1, 1, 2);
			grid.add(compTextField, 2, 2);
			grid.add(posName, 0, 3);
			grid.add(colonText2, 1, 3);
			grid.add(posTextField, 2, 3);
			grid.add(refNo, 0, 4);
			grid.add(colonText3, 1, 4);
			grid.add(refNoTextField, 2, 4);
			grid.add(cityName, 0, 5);
			grid.add(colonText4, 1, 5);
			grid.add(cityNameTextField, 2, 5);
			grid.add(stateName, 0, 6);
			grid.add(colonText5, 1, 6);
			grid.add(stateNameComboBox, 2, 6);
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
			submitHBtn.setId("submitHBtn");
			submitHBtn.setAlignment(Pos.BOTTOM_RIGHT);
			submitHBtn.getChildren().add(submitBtn);
			grid.add(submitHBtn, 0, 7, 2, 1);
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
			cancelHBtn.setId("cancelHBtn");
			cancelHBtn.setAlignment(Pos.CENTER);
			cancelHBtn.getChildren().add(cancelBtn);
			grid.add(cancelHBtn, 2, 7);
			cancelBtn.setOnAction(event ->
			{
				getWindows.getMainWindow(logPageStage);
			});
			//final adding to layout
			dataEntryScene = new Scene(grid, 800, 600);
			dataEntryScene.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
			logPageStage.setTitle("Job Application Tracking System");
			logPageStage.getIcons().add(new Image("file:hireme.png"));
			logPageStage.setScene(dataEntryScene);
			logPageStage.show();			
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
}
