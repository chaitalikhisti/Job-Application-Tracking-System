# Job-Application-Tracking-System
The basic function of this application is keeping a track of all the job applications made by a job seeker. The idea stems from the fact that it is difficult to keep a record of applications and avoid re-applying to the same positions by checking an extensive file like Excel.

## Application setup
The following details provide an overview of packages and utilities which are a substantial part of the source folder of the application:

### Pre-requisites:
SQL Server

### Application packages and their contents:

#### application package
##### Main.java
Java class with the main method having options of data entry, data search and statistical display of application count.
##### application.css
CSS file that contains styling for elements in all classes
#### newEntry package
#####  dataEntry.java
Java class containing data entry fields with company, position, city, position and date being mandatory fields whereas application ID and comments being the optional fields. Comments can include details of the recruiter details, people who have referred, job agency names (Glassdoor, Indeed, …), etc. preferably in a concise manner.
#### searchEntry package
##### searchData.java
Java class containing data search criteria like date of application, company, position, city, state, application ID and comments with search text field.
#### searchDisplay package
##### displayEntries.java
Java class using TableView component of JavaFX for displaying the data which matches the search criteria specified
#### displayCharts package
##### displayChart.java
Java class that provides the count of applications on a weekly, monthly and yearly basis depending on user choice
#### utilities package
##### databaseConnection.java
Java class for establishing connection with the database required for data entry and search transactions.
Enter your server location by replacing “//dbURL” in “jdbc:mysql://dbURL” and enter username and password using during SQL Server setup for the resppective fields in
```
dbURL = "jdbc:mysql://dbURL";
username = "root";
password = "password";
```
Run the following commands to create a schema and table inside it for data *(Note: these statements are specifically for MySQL JDBC driver, there may be minor changes depending on different JDBC drivers and in the IDEs used. Here, MySQL Workbench is used)*
* Create schema 
```
CREATE SCHEMA `jobdetails` ;
```
* Create table
```
CREATE TABLE `jobdata` (
  `App No` int(10) NOT NULL,
  `Date` varchar(45) NOT NULL,
  `Company` varchar(200) NOT NULL,
  `Position` varchar(200) NOT NULL,
  `City` varchar(45) NOT NULL,
  `State` varchar(45) NOT NULL,
  `Ref No` varchar(200) DEFAULT NULL,
  `Comments` varchar(400) DEFAULT NULL,
  PRIMARY KEY (`App No`),
  UNIQUE KEY `App No_UNIQUE` (`App No`));
  ```
##### GetWindows.java
Java class with methods for loading windows based on user selection
##### dataEntryUtil.java
Java class for supporting methods with SQL queries for performing data entry in the database
##### displayChartSelections.java
Java class for selection dropdowns in the view statistics window
##### displayChartUtil.java
Java class with supporting methods to extract required data from database and display on a graphical chart platform.
 
## License
This project is licensed under the MIT License - see the [License.txt](LICENSE.txt) file for details.

## Acknowledgement
The copyrights of the icons used are limited to the respective owners and so are few parts of the source code. 
Currently, the application primarily meets the basic requirements of logging the job application details excluding the functionalities of editing or deleting the data once entered. These can be developed with future versions of this application.
