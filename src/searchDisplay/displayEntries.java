package searchDisplay;

import application.Main;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.*; // for FXCollections and ObservableList
import javafx.geometry.*; // for Insets and Pos
import javafx.scene.Scene;
import javafx.scene.control.*; // for Alerts, Button, TableColumn
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.image.Image;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*; // for GridPane, HBox, VBox
import javafx.scene.text.*; // for Font, FontWeight, Text
import javafx.stage.Stage;
import javafx.util.Callback;
import utilities.dataEntryUtil;
import utilities.databaseConnection;
import utilities.getWindows;
import java.sql.*; //for connection, resultset, statement
import java.time.LocalDate;

public class displayEntries extends Application 
{
	GridPane grid;
	Text sceneTitle, invisibleText, noOfEntries;
	private TableView dispTable = new TableView();
	private ObservableList<ObservableList> data;
	final VBox vBox  = new VBox();
	Scene displayScene;
	Button cancelBtn;
	HBox cancelHBtn;
	Alert errorAlert;
	Connection c = databaseConnection.establishConnection();
	boolean connStatus = databaseConnection.getConnFlag();
	Statement st = null, st2 = null;
	String searchTerm;	
	LocalDate searchDate;
	
	public void start(Stage displayPageStage, String searchSelectionString, TextField searchTextField) 
	{
		try
		{
			searchTerm = searchTextField.getText();
			String emptyString = "                                                            ";
			//layout container
			grid = new GridPane();
			grid.setAlignment(Pos.TOP_CENTER);
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(25, 25, 25, 25));
			//nodes
			sceneTitle = new Text("                       Search Results");
			sceneTitle.setId("dataDisplaySceneTitle");
			invisibleText = new Text("");
			invisibleText.setId("invisibleText3");
			noOfEntries = new Text("");
			noOfEntries.setId("noOfEntries");
			//button
			cancelBtn =  new Button("OK");
			cancelHBtn = new HBox(20);
			cancelHBtn.setId("displayCancelHBtn");
			cancelHBtn.setAlignment(Pos.CENTER);
			cancelHBtn.getChildren().add(cancelBtn);
			//alert
			errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setTitle("Error");
			errorAlert.setHeaderText(null);
			//button event
			cancelBtn.setOnAction(event ->
			{
				getWindows.getMainWindow(displayPageStage);
			});
			if (!(connStatus = databaseConnection.getConnFlag()))
			{
				//connection loss alert
				String errorString = "Database Connection Unavailable";
				errorAlert.setContentText(errorString);
				errorAlert.showAndWait();
			}
			else
			{
				//count number of entries
				st2 = c.createStatement();
				String str2 = "SELECT COUNT(`App No`) FROM `jobdetails`.`jobdata` WHERE `" +searchSelectionString+ "` LIKE '%" +searchTerm+ "%'";
				ResultSet rs2 = st2.executeQuery(str2);
				rs2.next();
				int noOfApps = rs2.getInt(1);
				if (noOfApps > 1)
				{
					noOfEntries.setText(emptyString+ "Found " +noOfApps+ " entries that match the search criteria");
				}
				else
				{
					noOfEntries.setText(emptyString+ "Found " +noOfApps+ " entry that match the search criteria");
				}
				//database query and entry in dynamic table
				st = c.createStatement();
				String str = "SELECT * FROM `jobdetails`.`jobdata` WHERE `" +searchSelectionString+ "` LIKE '%" +searchTerm+ "%'";
		    	ResultSet rs = st.executeQuery(str);
				rs.beforeFirst();
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
	                	col.setPrefWidth(175);
	                	col.setMaxWidth(200);
	                }
	                else if (rs.getMetaData().getColumnName(i+1).equals("Position"))
	                {
	                	col = new TableColumn("Position");
	                	col.setMinWidth(250);
	                	col.setMaxWidth(275);
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
					String errorString = "No records found for given " +searchSelectionString+ "...Please try again..";
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
					dispTable.setPrefSize(700, 300);
					//add nodes to layout container
					grid.add(sceneTitle, 0, 0);
					grid.add(invisibleText, 0, 1);
					grid.add(noOfEntries, 0, 2);
					grid.add(dispTable, 0, 3);
					grid.add(cancelHBtn, 0, 4);
					//final adding to layout
					displayScene = new Scene(grid, 800, 600);
					displayScene.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
					displayPageStage.setTitle("Job Application Tracking System");
					displayPageStage.getIcons().add(new Image("file:hireme.png"));
					displayPageStage.setScene(displayScene);
					displayPageStage.show();
				}
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

	public void alternativeStart(Stage displayPageStage) 
	{
		try
		{
			String emptyString = "                                                            ";
			//layout container
			grid = new GridPane();
			grid.setAlignment(Pos.TOP_CENTER);
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(25, 25, 25, 25));
			//nodes
			sceneTitle = new Text("                       Search Results");
			sceneTitle.setId("dataDisplaySceneTitle");
			invisibleText = new Text("");
			invisibleText.setId("invisibleText3");
			noOfEntries = new Text("");
			noOfEntries.setId("noOfEntries");
			//button
			cancelBtn =  new Button("OK");
			cancelHBtn = new HBox(20);
			cancelHBtn.setId("displayCancelHBtn");
			cancelHBtn.setAlignment(Pos.CENTER);
			cancelHBtn.getChildren().add(cancelBtn);
			//alert
			errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setTitle("Error");
			errorAlert.setHeaderText(null);
			//button event
			cancelBtn.setOnAction(event ->
			{
				getWindows.getMainWindow(displayPageStage);
			});
			if (!(connStatus = databaseConnection.getConnFlag()))
			{
				//connection loss alert
				String errorString = "Database Connection Unavailable";
				errorAlert.setContentText(errorString);
				errorAlert.showAndWait();
			}
			else
			{
				//count number of entries
				st2 = c.createStatement();
				String str2 = "SELECT COUNT(`App No`) FROM `jobdetails`.`jobdata`";
				ResultSet rs2 = st2.executeQuery(str2);
				rs2.next();
				int noOfApps = rs2.getInt(1);
				if (noOfApps > 1)
				{
					noOfEntries.setText(emptyString+ "Found " +noOfApps+ " entries that match the search criteria");
				}
				else
				{
					noOfEntries.setText(emptyString+ "Found " +noOfApps+ " entry that match the search criteria");
				}
				//database query and entry in dynamic table
				st = c.createStatement();
				String str = "SELECT * FROM `jobdetails`.`jobdata`";
		    	ResultSet rs = st.executeQuery(str);
				rs.beforeFirst();
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
	                	col.setPrefWidth(175);
	                	col.setMaxWidth(200);
	                }
	                else if (rs.getMetaData().getColumnName(i+1).equals("Position"))
	                {
	                	col = new TableColumn("Position");
	                	col.setMinWidth(250);
	                	col.setMaxWidth(275);
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
					String errorString = "No records found...";
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
					dispTable.setPrefSize(700, 300);
					//add nodes to layout container
					grid.add(sceneTitle, 0, 0);
					grid.add(invisibleText, 0, 1);
					grid.add(noOfEntries, 0, 2);
					grid.add(dispTable, 0, 3);
					grid.add(cancelHBtn, 0, 4);
					//final adding to layout
					displayScene = new Scene(grid, 800, 600);
					displayScene.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
					displayPageStage.setTitle("Job Application Tracking System");
					displayPageStage.getIcons().add(new Image("file:hireme.png"));
					displayPageStage.setScene(displayScene);
					displayPageStage.show();
				}
			}			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}		
	}
}
