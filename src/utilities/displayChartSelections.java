package utilities;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Locale;

import javafx.scene.control.ComboBox;

public class displayChartSelections 
{    
    public static ComboBox<String> getWeekSelection()
    {
    	final ComboBox<String> weekNoComboBox = new ComboBox<String>();
    	weekNoComboBox.setVisibleRowCount(4);
    	//declare variables
    	LocalDate refDate, refEndDate;
    	WeekFields weekField = WeekFields.of(Locale.getDefault());
		LocalDate refStartDate, refWeekStart, yesterday;
    	LocalDate startDate = null, endDate = null, forLoopStartDate = null;
    	Month currentMonth;
		String weekName, regex, smallStartWeekDay, startDateDetails, smallEndWeekDay;
		String endDateDetails, weekDay, monthName, smallMonthName, dayAndDate;
      	int lastWeek;
  		refDate = LocalDate.now();
		refEndDate = LocalDate.of(refDate.getYear(), 12, 31);
    	//calculate total number of weeks in a year
		refStartDate = LocalDate.of(refDate.getYear(), 01, 01);
		TemporalField fieldUS = WeekFields.of(Locale.US).dayOfWeek();
		refWeekStart = refStartDate.with(fieldUS, 1);
		lastWeek = refEndDate.get(weekField.weekOfYear()) - 1; // reduced by 1 considering sunday
		for (int i = 1; i <= lastWeek; i++)
    	{
			startDate = refWeekStart;
			smallStartWeekDay = startDate.getMonth().name().substring(0, Math.min(startDate.getMonth().name().length(), 3));
			startDateDetails = smallStartWeekDay+ " " +startDate.getDayOfMonth();
			endDate = startDate.plus(Period.ofDays(6));
			smallEndWeekDay = endDate.getMonth().name().substring(0, Math.min(endDate.getMonth().name().length(), 3));
			endDateDetails = smallEndWeekDay+ " " +endDate.getDayOfMonth();	
			refWeekStart = endDate.plus(Period.ofDays(1));
			String str = startDateDetails+ " - " +endDateDetails;
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
    		"2016", "2017", "2018", "2019", "2020"
    	);
        yearComboBox.setValue("Year");
        return yearComboBox;
    }
}
