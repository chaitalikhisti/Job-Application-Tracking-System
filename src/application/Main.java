package application;
	
import javafx.application.Application;
import javafx.geometry.*; // for Insets, Pos
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*; // for Button, Label, PasswordField, TextField
import javafx.scene.layout.*; // for GridPane, HBox
import javafx.scene.paint.Color;
import javafx.scene.text.*; // for Font, FontWeight, Text
import newEntry.*;
import searchEntry.*;
import utilities.getWindows;

public class Main extends Application 
{
	@Override
	public void start(Stage welcomePageStage) 
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
			Text sceneTitle = new Text("Welcome to the Application!!!");
			sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 25));
			//add nodes to layout
			grid.add(sceneTitle, 0, 0, 2, 2); //PaneName.add(leafNodeName, columnNo, rowNo, columnSpan, rowSpan)
			grid.setGridLinesVisible(false); //gridLines for testing purpose
			//newEntry button
			Button newBtn =  new Button("New Entry");
			HBox newHBtn = new HBox(10);
			newHBtn.setAlignment(Pos.CENTER);
			newHBtn.getChildren().add(newBtn);
			grid.add(newHBtn, 0, 4);
			final Text newActionTarget = new Text();
			grid.add(newActionTarget, 0, 6);
			newBtn.setOnAction(event -> 
			{
				newActionTarget.setFill(Color.FIREBRICK);
				getWindows.getDataEntryWindow(welcomePageStage);
			});
			//searchEntry button
			Button btn =  new Button("Search Entry");
			HBox hBtn = new HBox(10);
			hBtn.setAlignment(Pos.CENTER);
			hBtn.getChildren().add(btn);
			grid.add(hBtn, 1, 4);
			final Text searchActionTarget = new Text();
			grid.add(searchActionTarget, 1, 6);
			btn.setOnAction(event -> 
			{
				searchActionTarget.setFill(Color.FIREBRICK);
				getWindows.getSearchWindow(welcomePageStage);
			});

			//final adding to layout
			Scene welcomeScene = new Scene(grid, 400, 400);
			welcomeScene.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
			welcomePageStage.setTitle("Job Application Tracking System");
			welcomePageStage.setScene(welcomeScene);
			welcomePageStage.show();
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
