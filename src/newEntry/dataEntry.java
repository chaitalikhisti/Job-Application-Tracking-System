package newEntry;

import javafx.application.Application;
import javafx.geometry.*; // for Insets, Pos
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*; // for Button, Label, TextField
import javafx.scene.layout.*; // for GridPane, HBox
import javafx.scene.paint.Color;
import javafx.scene.text.*; // for Font, FontWeight, Text

public class dataEntry extends Application 
{

	@Override
	public void start(Stage primaryStage) 
	{
		try 
		{
			//grid layout
			GridPane grid = new GridPane();
			grid.setAlignment(Pos.CENTER);
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(25, 25, 25, 25));
			//nodes
			Text sceneTitle = new Text("Enter Application Details : ");
			sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
			Label compName = new Label("Company Name : ");
			TextField compTextField = new TextField();
			Label posName = new Label("Position : ");
			TextField posTextField = new TextField();
			Label refNo = new Label("Reference No. : ");
			TextField refNoTextField = new TextField();			
			Label cityName = new Label("City : ");
			TextField cityNameTextField = new TextField();
			Label stateName = new Label("State : ");
			TextField stateNameTextField = new TextField();
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
			grid.add(stateNameTextField, 1, 5);
			grid.setGridLinesVisible(false); //gridLines for testing purpose
			//submit button
			Button submitBtn =  new Button("SUBMIT");
			HBox submitHBtn = new HBox(10);
			submitHBtn.setAlignment(Pos.BOTTOM_RIGHT);
			submitHBtn.getChildren().add(submitBtn);
			grid.add(submitHBtn, 0, 6);
			final Text actiontarget = new Text();
			grid.add(actiontarget, 1, 7);
			submitBtn.setOnAction(event -> 
			{
				actiontarget.setFill(Color.FIREBRICK);
				actiontarget.setText("Submit Button Pressed"); //should give a dialog box indicating successful completion
			});
			//cancel button
			Button btn =  new Button("CANCEL");
			HBox hBtn = new HBox(10);
			//hBtn.setAlignment(Pos.BOTTOM_LEFT);
			hBtn.setAlignment(Pos.CENTER);
			hBtn.getChildren().add(btn);
			grid.add(hBtn, 1, 6);
			//final adding to layout
			Scene scene = new Scene(grid, 400, 400);
			scene.getStylesheets().add(dataEntry.class.getResource("dataEntryCSS.css").toExternalForm());
			primaryStage.setTitle("Job Application Tracking System");
			primaryStage.setScene(scene);
			primaryStage.show();
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args) 
	{
		launch(args);
	}
}
