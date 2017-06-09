package application;
	
import javafx.application.Application;
import javafx.geometry.*; // for Insets, Pos
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*; // for Button, Label, TextField
import javafx.scene.image.Image;
import javafx.scene.layout.*; // for GridPane, HBox
import javafx.scene.text.*; // for Font, FontWeight, Text
import utilities.getWindows;

public class Main extends Application 
{
	GridPane grid;
	Text sceneTitle, invisibleText;
	Button newBtn, searchBtn, statsBtn;
	HBox newHBtn, searchHBtn, statsHBtn;
	Scene welcomeScene;
	
	@Override
	public void start(Stage welcomePageStage) 
	{		
		try 
		{
			//layout container
			grid = new GridPane();
			grid.setAlignment(Pos.CENTER);
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(25, 25, 25, 25));
			//nodes
			invisibleText = new Text("");
			invisibleText.setId("invisibleText");
			sceneTitle = new Text("Job Application Tracking System");
			sceneTitle.setId("mainSceneTitle");
			//buttons
			newBtn =  new Button("New Entry");
			newHBtn = new HBox(20);
			newHBtn.setId("newHBtn");
			newHBtn.setAlignment(Pos.BOTTOM_RIGHT);
			newHBtn.getChildren().add(newBtn);
			searchBtn =  new Button("Search Entry");
			searchHBtn = new HBox(20);
			searchHBtn.setId("searchHBtn");
			searchHBtn.setAlignment(Pos.BOTTOM_LEFT);
			searchHBtn.getChildren().add(searchBtn);
			statsBtn =  new Button("View Statistics");
			statsHBtn = new HBox(20);
			statsHBtn.setId("statsHBtn");
			statsHBtn.setAlignment(Pos.BOTTOM_LEFT);
			statsHBtn.getChildren().add(statsBtn);
			//button events			
			newBtn.setOnAction(event -> 
			{
				getWindows.getDataEntryWindow(welcomePageStage);
			});
			searchBtn.setOnAction(event -> 
			{
				getWindows.getSearchWindow(welcomePageStage);
			});
			statsBtn.setOnAction(event -> 
			{
				getWindows.getStatsResultsWindow(welcomePageStage);
			});
			//add nodes to layout container
			grid.add(invisibleText, 0, 0, 3, 4); //PaneName.add(leafNodeName, columnNo, rowNo, columnSpan, rowSpan)
			grid.add(sceneTitle, 0, 3, 3, 4); 
			grid.add(newHBtn, 0, 7);
			grid.add(searchHBtn, 1, 7);
			grid.add(statsHBtn, 2, 7);
			grid.setGridLinesVisible(false); //gridLines for testing purpose
			//final adding to layout
			welcomeScene = new Scene(grid, 800, 600);
			welcomeScene.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
			welcomePageStage.setTitle("Job Application Tracking System");
			welcomePageStage.getIcons().add(new Image("file:hireme.png"));
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
