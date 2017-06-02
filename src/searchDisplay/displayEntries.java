package searchDisplay;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import newEntry.dataEntry;

public class displayEntries extends Application 
{
	Text sceneTitle;
	private TableView dispTable = new TableView();
	private ObservableList<ObservableList> data;
	final VBox vBox  = new VBox();
	Scene displayScene;
	
	
	@Override
	public void start(Stage displayPageStage) 
	{
		//layout container
		vBox.setSpacing(5);
		vBox.setPadding(new Insets(25, 25, 25, 25));
		//nodes
		sceneTitle = new Text("Search Results: ");
		sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		//table layout
		dispTable.setEditable(true);
		TableColumn firstCol = new TableColumn("Column 1");
		TableColumn secondCol = new TableColumn("Column 2");
		TableColumn thirdCol = new TableColumn("Column 3");
		/* additional table properties
		to hide visibility of any of the columns: column_name.setVisible(false);
		for sub-divisions in table columns (nested columns):
			TableColumn third1 = new TableColumn("3 - 1");
			TableColumn third2 = new TableColumn("3 - 2");
			thirdCol.getColumns().addAll(third1, third2);
		*/
		dispTable.getColumns().addAll(firstCol, secondCol, thirdCol);
		//table components
		//data = FXCollections.observableArrayList();
		//add nodes to layout container
		vBox.getChildren().addAll(sceneTitle, dispTable);
		//final adding to layout
		displayScene = new Scene(new Group());
		((Group) displayScene.getRoot()).getChildren().addAll(vBox);
		displayScene.getStylesheets().add(dataEntry.class.getResource("dataEntryCSS.css").toExternalForm());
		displayPageStage.setTitle("Job Application Tracking System");
		displayPageStage.setScene(displayScene);
		displayPageStage.show();
	}

	public static void main(String[] args) 
	{
		launch(args);
	}
}
