package newEntry;

import application.Main;
import javafx.application.Application;
import javafx.geometry.*; // for Insets, Pos
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.Scene;
import javafx.scene.control.*; // for Button, Label, TextField
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.*; // for GridPane, HBox
import javafx.scene.text.*; // for Text
import java.time.LocalDate;
import utilities.dataEntryUtil;
import utilities.getWindows;

public class dataEntry extends Application 
{
	//declaring all components
	GridPane grid;
	Text sceneTitle, invisibleText;
	Label compName, posName, refNo, cityName, stateName, commentName, dateName;
	Label colonText1, colonText2, colonText3, colonText4, colonText5, colonText6, colonText7;
	TextField compTextField, posTextField, refNoTextField, cityNameTextField, commentNameTextField;
	String asterisk, colon;
	final ComboBox<String> stateNameComboBox = new ComboBox<String>();
	DatePicker chooseDate;
	Alert errorAlert, errorAlert1, errorAlert2, errorAlert3, submitAlert;
	Button submitBtn, cancelBtn;
	HBox submitHBtn, cancelHBtn;
	Scene dataEntryScene;
	String errorString = "";
	boolean connStatus = false;
	
	@Override
	public void start(Stage logPageStage) 
	{
		asterisk = "*";
		colon = ":";
		try 
		{
			//layout container
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
				"MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", 
				"NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC",
				"SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI","WY", "Other"
	        ); 
			stateNameComboBox.setValue("Select State");
			stateNameComboBox.setVisibleRowCount(6);
			stateNameComboBox.setPrefSize(400, 35);
			commentName = new Label("Comment");
			colonText6 = new Label("  " +colon);
			commentNameTextField = new TextField();
			commentNameTextField.setPrefSize(300, 35);
			dateName = new Label("Application Date");
			colonText7 = new Label(" "+colon);
			chooseDate = new DatePicker();
			chooseDate.setPromptText("MM/DD/YYYY");
			/*
			 *  Reference for Callback function of date-picker:
			 *  https://docs.oracle.com/javase/8/javafx/user-interface-tutorial/date-picker.htm
			 */
			final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() 
			{
                @Override
                public DateCell call(final DatePicker datePicker) 
                {
                    return new DateCell() 
                    {
                        @Override
                        public void updateItem(LocalDate item, boolean empty) 
                        {
                            super.updateItem(item, empty);
                           
                            if (item.isAfter(LocalDate.now()) )
                            {
                                setDisable(true);
                            }   
                        }
                    };
                }
		    };
		    chooseDate.setDayCellFactory(dayCellFactory);
			chooseDate.setShowWeekNumbers(true);
			chooseDate.setPrefSize(400, 35);
			submitBtn =  new Button("SUBMIT");
			submitHBtn = new HBox(10);
			submitHBtn.setId("submitHBtn");
			submitHBtn.setAlignment(Pos.BOTTOM_RIGHT);
			submitHBtn.getChildren().add(submitBtn);
			cancelBtn =  new Button("CANCEL");
			cancelHBtn = new HBox(10);
			cancelHBtn.setId("cancelHBtn");
			cancelHBtn.setAlignment(Pos.CENTER);
			cancelHBtn.getChildren().add(cancelBtn);
			//dialog boxes for buttons
			//alert
			errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setTitle("Error");
			errorAlert.setHeaderText(null);
			submitAlert = new Alert(AlertType.CONFIRMATION);
			submitAlert.setTitle("Successful!!!");
			submitAlert.setHeaderText(null);
			submitAlert.setContentText("Data successfully updated");
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
			grid.add(dateName, 0, 7);
			grid.add(colonText7, 1, 7);
			grid.add(chooseDate, 2, 7);
			grid.add(commentName, 0, 8);
			grid.add(colonText6, 1, 8);
			grid.add(commentNameTextField, 2, 8);
			grid.add(submitHBtn, 0, 9, 2, 1);
			grid.add(cancelHBtn, 2, 9);
			grid.setGridLinesVisible(false);
			//button events
			submitBtn.setOnAction(event -> 
			{
				if (compTextField.getText().isEmpty() || posTextField.getText().isEmpty() || cityNameTextField.getText().isEmpty() || stateNameComboBox.getSelectionModel().isEmpty())
				{
					errorString = "All fields with * must be filled";
					errorAlert.setContentText(errorString);
					errorAlert.showAndWait();
				}				
				else
				{
					if (chooseDate.getValue() == null)
					{
						chooseDate.setValue(LocalDate.now());
					}
					else if (chooseDate.getValue().isAfter(LocalDate.now()))
					{
						errorString = "Enter valid date";
						errorAlert.setContentText(errorString);
						errorAlert.showAndWait();
					}
					else
					{
						//check and enter data in database
						if (dataEntryUtil.enterData(compTextField, posTextField, refNoTextField, cityNameTextField, stateNameComboBox, chooseDate, commentNameTextField))
						{
							submitAlert.showAndWait();
							//clear all fields for next data entry
							compTextField.clear();
							posTextField.clear();
							refNoTextField.clear();
							cityNameTextField.clear();
							stateNameComboBox.setValue("Select State");
							chooseDate.setValue(null);
							getWindows.getMainWindow(logPageStage);							
						}						
						else if (!(connStatus = dataEntryUtil.getDBConnFlag()))
						{
							//connection loss alert
							errorString = "Database Connection Unavailable";
							errorAlert.setContentText(errorString);	
							errorAlert.showAndWait();
						}
						else
						{
							errorString = "A valid record for this entry already exists";
							errorAlert.setContentText(errorString);							
							errorAlert.showAndWait();
						}		
					}			
				}
			});
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
