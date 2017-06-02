package searchDisplay;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import newEntry.dataEntry;
import utilities.databaseConnection;

public class displayEntries extends Application 
{
	Text sceneTitle;
	private TableView dispTable = new TableView();
	private ObservableList<ObservableList> data;
	final VBox vBox  = new VBox();
	Scene displayScene;
	Alert errorAlert;
	Connection c = databaseConnection.establishConnection();
	Statement st = null;
	String searchTerm;
	
	
	
	public void start(Stage displayPageStage, String searchSelectionString, TextField searchTextField) 
	{
		try
		{
			searchTerm = searchTextField.getText();
			//layout container
			vBox.setSpacing(5);
			vBox.setPadding(new Insets(25, 25, 25, 25));
			//nodes
			sceneTitle = new Text("Search Results: ");
			sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
			errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setTitle("Error");
			errorAlert.setHeaderText(null);
			//database query and entry in dynamic table
			st = c.createStatement();
	    	String str = "SELECT * FROM `jobdetails`.`jobdata` WHERE `" +searchSelectionString+ "` LIKE '%" +searchTerm+ "%'";
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
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
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
				while(rs.next());
				dispTable.setItems(data);
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
