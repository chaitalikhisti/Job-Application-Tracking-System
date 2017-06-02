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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
	Connection c = databaseConnection.establishConnection();
	Statement st = null;
	String searchTerm;
	
	
	
	public void start(Stage displayPageStage, String searchSelectionString, TextField searchTextField) 
	{
		try
		{
			//searchCriteria = searchSelection.getSelectedToggle().toString();
			searchTerm = searchTextField.getText();
			//layout container
			vBox.setSpacing(5);
			vBox.setPadding(new Insets(25, 25, 25, 25));
			//nodes
			sceneTitle = new Text("Search Results: ");
			sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
			//database query and entry in dynamic table
			st = c.createStatement();
	    	String str = "SELECT * FROM `jobdetails`.`jobdata` WHERE `" +searchSelectionString+ "` = '" +searchTerm+ "'";
			//String str = "SELECT * FROM `jobdetails`.`jobdata`";
			System.out.println(str);
			ResultSet rs = st.executeQuery(str);
			rs.beforeFirst();
			//dynamic table layout
			data = FXCollections.observableArrayList();
			//table columns (fixed)
			/*
			TableColumn appNo = new TableColumn("App No.");
			appNo.setMaxWidth(80);
			TableColumn comp = new TableColumn("Company");
			comp.setMinWidth(150);
			TableColumn pos = new TableColumn("Position");
			pos.setMinWidth(300);
			TableColumn city = new TableColumn("City");
			city.setMinWidth(100);
			TableColumn state = new TableColumn("State");
			state.setMaxWidth(50);
			TableColumn refNo = new TableColumn("Ref. No.");
			dispTable.getColumns().addAll(appNo, comp, pos, city, state, refNo);
			*/
			for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++)
			{
                //We are using non property style for making dynamic table
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
				System.out.println("Empty dataset");
			}
			//int no = 0;
			while(rs.next())
			//while(no < 5)
			{
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i <= rs.getMetaData().getColumnCount(); i++)
                {
                    //Iterate Column
                    row.add(rs.getString(i));
                	//row.add("Item" +no);
					//no++;
                }
                System.out.println("Row [1] added "+row );
                data.add(row);
            }

			/*
			for (int iCol = 0; iCol < 4; iCol++)
			{
				final int j = iCol;  
				TableColumn col = new TableColumn("Column "+iCol);
				col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>()
				{                    
	                public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) 
	                {                                                                                              
	                    return new SimpleStringProperty(param.getValue().get(j).toString());                        
	                }                    
	            });
				dispTable.getColumns().addAll(col);
			}
			int i = 1;
			for (int iRow = 1; iRow < 8; iRow++)
			{
				ObservableList<String> row = FXCollections.observableArrayList();
				for (int jCol = 0; jCol < 4; jCol++)
				{
					row.add("Item" +i);
					i++;
				}
				data.add(row);
			}
			*/
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
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		//table layout
		/*
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
		/*
		dispTable.getColumns().addAll(firstCol, secondCol, thirdCol);
		//table components
		data = FXCollections.observableArrayList();
		*/
	}

	public static void main(String[] args) 
	{
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception 
	{
			
	}
}
