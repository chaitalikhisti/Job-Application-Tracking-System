package searchDisplay;

import java.awt.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import application.Main;
import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.image.Image;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import newEntry.dataEntry;
import utilities.databaseConnection;
import utilities.getWindows;

public class displayEntries extends Application 
{
	Text sceneTitle, invisibleText;
	private TableView dispTable = new TableView();
	private ObservableList<ObservableList> data;
	final VBox vBox  = new VBox();
	Scene displayScene;
	Button cancelBtn;
	HBox cancelHBtn;
	Alert errorAlert;
	Connection c = databaseConnection.establishConnection();
	Statement st = null;
	String searchTerm;	
	LocalDate searchDate;
	
	public void start(Stage displayPageStage, String searchSelectionString, TextField searchTextField, DatePicker someDatePicker) 
	{
		try
		{
			searchTerm = searchTextField.getText();
			//layout container
			vBox.setSpacing(5);
			vBox.setPadding(new Insets(25, 25, 25, 25));
			vBox.setAlignment(Pos.TOP_CENTER);
			//nodes
			sceneTitle = new Text("Search Results");
			sceneTitle.setId("dataDisplaySceneTitle");
			invisibleText = new Text("");
			invisibleText.setId("invisibleText3");
			//cancel button
			cancelBtn =  new Button("OK");
			cancelHBtn = new HBox(20);
			cancelHBtn.setAlignment(Pos.CENTER);
			cancelHBtn.getChildren().add(cancelBtn);
			cancelBtn.setOnAction(event ->
			{
				getWindows.getMainWindow(displayPageStage);
			});
			sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
			errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setTitle("Error");
			errorAlert.setHeaderText(null);
			//database query and entry in dynamic table
			st = c.createStatement();
			String str = "SELECT * FROM `jobdetails`.`jobdatatrial` WHERE `" +searchSelectionString+ "` LIKE '%" +searchTerm+ "%'";
			System.out.println(str);
	    	ResultSet rs = st.executeQuery(str);
			rs.beforeFirst();
			//dynamic table layout
			data = FXCollections.observableArrayList();
			/* Reference for Column and Row entries :
			*  https://stackoverflow.com/questions/18941093/how-to-fill-up-a-tableview-with-database-data
			*/
			for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++)
			{
                final int j = i; 
                TableColumn col = null;
                if (rs.getMetaData().getColumnName(i+1).equals("App No"))
                {
                	 col = new TableColumn("No.");
                	 col.setMinWidth(50);
                }
                else if (rs.getMetaData().getColumnName(i+1).equals("Date"))
                {
                	col = new TableColumn("Date");
                	col.setMaxWidth(80);
                }
                else if (rs.getMetaData().getColumnName(i+1).equals("Company"))
                {
                	col = new TableColumn("Company");
                	col.setPrefWidth(100);
                	col.setMaxWidth(150);
                }
                else if (rs.getMetaData().getColumnName(i+1).equals("Position"))
                {
                	col = new TableColumn("Position");
                	col.setMinWidth(150);
                	col.setMaxWidth(200);
                }
                else if (rs.getMetaData().getColumnName(i+1).equals("City"))
                {
                	col = new TableColumn("City");
                	col.setMinWidth(80);
                	col.setMaxWidth(100);
                }
                else if (rs.getMetaData().getColumnName(i+1).equals("State"))
                {
                	col = new TableColumn("State");
                	col.setMinWidth(80);
                	col.setMaxWidth(80);
                }
                else if (rs.getMetaData().getColumnName(i+1).equals("Ref No"))
                {
                	col = new TableColumn("App ID");
                	col.setMinWidth(80);
                	col.setMaxWidth(100);
                }
                else if (rs.getMetaData().getColumnName(i+1).equals("Comments"))
                {
                	col = new TableColumn("Comments");
                	col.setMinWidth(100);
                	col.setMinWidth(250);
                }
                else
                {
                	col = new TableColumn("Some Col");
                }
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>()
                {                    
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) 
                    {                                                                                              
                        return new SimpleStringProperty(param.getValue().get(j).toString());                        
                    }                    
                });
                dispTable.getColumns().addAll(col);
			}
			//data rows dynamic
			if (!rs.next())
			{
				String errorString = "No records found for " +searchTerm+ " in " +searchSelectionString+ "...Please try again..";
				errorAlert.setContentText(errorString);
				errorAlert.showAndWait();
			}
			else
			{
				do
				{
					//Iterate Row
	                ObservableList<String> row = FXCollections.observableArrayList();
	                for(int i=1 ; i <= rs.getMetaData().getColumnCount(); i++)
	                {
	                    //Iterate Column
	                    row.add(rs.getString(i));
	                }
	                data.add(row);
				}
				//testing ends
				while(rs.next());
				dispTable.setItems(data);
				dispTable.setPrefSize(700, 300);
				//add nodes to layout container
				vBox.getChildren().addAll(sceneTitle, invisibleText, dispTable, cancelHBtn);
				//final adding to layout
				displayScene = new Scene(new Group(), 800, 600);
				((Group) displayScene.getRoot()).getChildren().addAll(vBox);
				displayScene.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
				displayPageStage.setTitle("Job Application Tracking System");
				displayPageStage.getIcons().add(new Image("file:hireme.png"));
				displayPageStage.setScene(displayScene);
				displayPageStage.show();
			}			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	//add unimplemented methods
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
			
	}
}
