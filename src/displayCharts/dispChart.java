package displayCharts;

import application.Main;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utilities.displayChartSelections;
import utilities.displayChartUtil;
import utilities.getWindows;

public class dispChart extends Application 
{
	GridPane grid;
    Text sceneTitle, invisibleText, invisibleText1, invisibleText2;
    Button submitBtn, backBtn, weeklyBtn, monthlyBtn, yearlyBtn;
    HBox submitHBtn, backHBtn, weeklyHBtn, monthlyHBtn, yearlyHBtn;
    Node weekChart, monthChart, yearChart;
    ComboBox<String> weekNo, monthNo, yearMonthsNo;
    int selectedMonthNo;
    Scene chartScene;
    
	@Override 
    public void start(Stage statsPageStage) 
    {
        //layout container
    	grid = new GridPane();
    	grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		ColumnConstraints col1 = new ColumnConstraints();
	    col1.setPercentWidth(25);
	    ColumnConstraints col2 = new ColumnConstraints();
	    col2.setPercentWidth(30);
	    ColumnConstraints col3 = new ColumnConstraints();
	    col3.setPercentWidth(25);
		//nodes
		sceneTitle = new Text("               Application Statics");
		sceneTitle.setId("chartDisplaySceneTitle");
		invisibleText = new Text("");
		invisibleText.setId("invisibleText4");
		invisibleText1 = new Text("");
		invisibleText1.setId("invisibleText5");
		invisibleText2 = new Text("");
		invisibleText2.setId("invisibleText6");
		//buttons
		weeklyBtn =  new Button("WEEKLY");
		weekNo = displayChartSelections.getWeekSelection();
		//String weekName = weekNo.getValue();
		//String stateName = someStateComboBox.getValue();
		//ComboBox<String> weekNo = new ComboBox<String>();
		weeklyBtn.setOnAction(event ->
		{ 
			if (grid.getChildren().contains(monthChart) || grid.getChildren().contains(yearChart))
			{
				grid.getChildren().remove(monthChart);
				grid.getChildren().remove(yearChart);
			}
			grid.add(weekNo, 0, 5);
			weekNo.setOnAction((event1) -> 
			{
				weekChart = displayChartUtil.weeklyChart(grid, weekNo);			
				grid.add(weekChart, 0, 2, 3, 1);
			});
		});
		weeklyHBtn = new HBox(10);
		weeklyHBtn.setAlignment(Pos.CENTER);
		weeklyHBtn.getChildren().add(weeklyBtn);
		monthlyBtn =  new Button("MONTHLY");
		monthNo = displayChartSelections.getMonthSelection();
		monthlyBtn.setOnAction(event ->
		{
			//monthChart = displayChartUtil.monthlyChart();
			if (grid.getChildren().contains(weekChart) || grid.getChildren().contains(yearChart))
			{
				grid.getChildren().remove(weekChart);
				grid.getChildren().remove(yearChart);
			}
			grid.add(monthNo, 1, 5);
			monthNo.setOnAction((event1) -> 
			{
				String monthName = monthNo.getValue();
				int monthInt = getMonthNo(monthName);
				//System.out.println(monthName+ " " +monthInt);
				monthChart = displayChartUtil.monthlyChart(grid, monthNo, monthInt, monthName);	
				grid.add(monthChart, 0, 2, 3, 1);
			});
		});
		monthlyHBtn = new HBox(10);
		monthlyHBtn.setAlignment(Pos.CENTER);
		monthlyHBtn.getChildren().add(monthlyBtn);
		yearlyBtn =  new Button("YEARLY");
		yearMonthsNo = displayChartSelections.getYearSelection();
		yearlyBtn.setOnAction(event ->
		{
			//grid.add(yearMonthsNo, 2, 5);
			//yearChart = displayChartUtil.yearlyChart();
			if (grid.getChildren().contains(weekChart) || grid.getChildren().contains(monthChart))
			{
				grid.getChildren().remove(weekChart);
				grid.getChildren().remove(monthChart);
			}
			grid.add(yearMonthsNo, 2, 5);
			yearMonthsNo.setOnAction((event1) -> 
			{
//				String monthName = monthNo.getValue();
//				int monthInt = getMonthNo(monthName);
				int yearInt = Integer.parseInt(yearMonthsNo.getValue());
				//System.out.println(monthName+ " " +monthInt);
				monthChart = displayChartUtil.yearlyChart(grid, yearMonthsNo, yearInt);	
				grid.add(yearChart, 0, 2, 3, 1);
			});
			//grid.add(yearChart, 0, 2, 3, 1);
		});
		yearlyHBtn = new HBox(10);
		yearlyHBtn.setAlignment(Pos.CENTER);
		yearlyHBtn.getChildren().add(yearlyBtn);
		backBtn =  new Button("BACK");
		backBtn.setOnAction(event ->
		{
			getWindows.getMainWindow(statsPageStage);
		});
		backHBtn = new HBox(10);
		backHBtn.setId("backHBtn");
		backHBtn.setAlignment(Pos.CENTER);
		backHBtn.getChildren().add(backBtn);
        //add elements to grid layout
        grid.getColumnConstraints().addAll(col1,col2,col3);
        grid.add(sceneTitle, 0, 0, 3, 1);
        grid.add(invisibleText, 0, 1, 3, 1);
        //grid.add(displayChartUtil.weeklyChart(), 0, 2, 3, 1);
        grid.add(invisibleText1, 0, 3, 3, 1);
        grid.add(weeklyHBtn, 0, 4);
        grid.add(monthlyHBtn, 1, 4);
        grid.add(yearlyHBtn, 2, 4);
        grid.add(backHBtn, 1, 6);
        chartScene  = new Scene(grid,800,600);    
        chartScene.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
        statsPageStage.setTitle("Job Application Tracking System");
        statsPageStage.getIcons().add(new Image("file:hireme.png"));
        statsPageStage.setScene(chartScene);
        statsPageStage.show();
    }
	
	public int getMonthNo(String someString)
	{
		if (someString.equalsIgnoreCase("january"))
		{
			selectedMonthNo = 1;
		}
		else if (someString.equalsIgnoreCase("february"))
		{
			selectedMonthNo = 2;
		}
		else if (someString.equalsIgnoreCase("march"))
		{
			selectedMonthNo = 3;
		}
		else if (someString.equalsIgnoreCase("april"))
		{
			selectedMonthNo = 4;
		}
		else if (someString.equalsIgnoreCase("may"))
		{
			selectedMonthNo = 5;
		}
		else if (someString.equalsIgnoreCase("june"))
		{
			selectedMonthNo = 6;
		}
		else if (someString.equalsIgnoreCase("july"))
		{
			selectedMonthNo = 7;
		}
		else if (someString.equalsIgnoreCase("august"))
		{
			selectedMonthNo = 8;
		}
		else if (someString.equalsIgnoreCase("september"))
		{
			selectedMonthNo = 9;
		}
		else if (someString.equalsIgnoreCase("october"))
		{
			selectedMonthNo = 10;
		}
		else if (someString.equalsIgnoreCase("november"))
		{
			selectedMonthNo = 11;
		}
		else
		{
			selectedMonthNo = 12;
		}
		return selectedMonthNo;
	}
}
