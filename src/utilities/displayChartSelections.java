package utilities;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

import javafx.scene.control.ComboBox;

public class displayChartSelections 
{    
    public static ComboBox<String> getWeekSelection()
    {
    	final ComboBox<String> weekNoComboBox = new ComboBox<String>();
    	weekNoComboBox.setVisibleRowCount(4);
        LocalDate refDate, refStartDate, refEndDate;
        int firstWeek, lastWeek;
    	refDate = LocalDate.now();
    	refStartDate = LocalDate.of(refDate.getYear(), 01, 01);
		refEndDate = LocalDate.of(refDate.getYear(), 12, 31);
		//get no. of weeks in the year
		WeekFields weekField = WeekFields.of(Locale.getDefault());
		firstWeek = refStartDate.get(weekField.weekOfYear());
		lastWeek = refEndDate.get(weekField.weekOfYear()) - 1; // reduced by 1 considering sunday
		for (int i = 1; i <= lastWeek; i++)
    	{
    		String str = "Week " +i;
    		weekNoComboBox.getItems().add(str);
    	}
    	weekNoComboBox.setValue("Week");
    	return weekNoComboBox;
    }
    
    public static ComboBox<String> getMonthSelection()
    {
    	final ComboBox<String> monthNoComboBox = new ComboBox<String>();
    	monthNoComboBox.setVisibleRowCount(4);
    	monthNoComboBox.getItems().addAll
    	(
    		"January", "February", "March", "April",
    		"May", "June", "July", "August", "September",
        	"October", "November", "December"
    	);
        monthNoComboBox.setValue("Month");
        return monthNoComboBox;
    }
    
    public static ComboBox<String> getYearSelection()
    {
    	final ComboBox<String> yearComboBox = new ComboBox<String>();
    	yearComboBox.setVisibleRowCount(4);
    	yearComboBox.getItems().addAll
    	(
    		"2017", "2018", "2019", "2020"
    	);
        yearComboBox.setValue("Year");
        return yearComboBox;
    }
}
