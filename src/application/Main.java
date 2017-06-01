package application;
	
import javafx.application.Application;
import javafx.geometry.*; // for Insets, Pos
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*; // for Button, Label, PasswordField, TextField
import javafx.scene.layout.*; // for GridPane, HBox
import javafx.scene.paint.Color;
import javafx.scene.text.*; // for Font, FontWeight, Text

public class Main extends Application 
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
			Text sceneTitle = new Text("Please Login: ");
			sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
			Label userName = new Label("User Name: ");
			TextField userTextField = new TextField();
			Label pw = new Label("Password: ");
			PasswordField pwField =  new PasswordField();
			//add nodes to layout
			grid.add(sceneTitle, 0, 0, 2, 1); //PaneName.add(leafNodeName, columnNo, rowNo, columnSpan, rowSpan)
			grid.add(userName, 0, 1);
			grid.add(userTextField, 1, 1);
			grid.add(pw, 0, 2);
			grid.add(pwField, 1, 2);
			grid.setGridLinesVisible(true); //gridLines for testing purpose
			//approval button
			Button btn =  new Button("Sign In");
			HBox hBtn = new HBox(10);
			hBtn.setAlignment(Pos.BOTTOM_RIGHT);
			hBtn.getChildren().add(btn);
			grid.add(hBtn, 1, 4);
			final Text actiontarget = new Text();
			grid.add(actiontarget, 1, 6);
			btn.setOnAction(event -> 
			{
				actiontarget.setFill(Color.FIREBRICK);
				actiontarget.setText("Sign In Button Pressed");
			});
			//final adding to layout
			Scene scene = new Scene(grid, 300, 275);
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
