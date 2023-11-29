# ScheduleManager
Title:	Customer Scheduler

Purpose: The purpose of this application is to grant users with the ability to view and manipulate appointment and customer data stored on a database. 
Users can read, update, and delete customer and appointment data by selecting appointments or customers from tables and choosing a service button underneath the table. 
Users can also create new customers and appointments data by clicking the add button underneath the corresponding table, filling out all required form input, and clicking the save button on the form.
Users can also view reports data by clicking the Reports button on the Main Menu page.

Author:	Patrick Abonado

Student Application Version:	2

Date:	11-29-23 

IDE:		IntelliJ Ultimate 2023.2.5

Java version:	Java JDK-17.0.1

JavaFX version:	JavaFX SDK 17

MySQL Connector Driver Version:		mysql-connector-java-8.0.29

Directions:

--Prior to running the application the MySQL database starter code must be implemented.  
The file is located in "ScheduleManager\Schedule-Manager-MySQL-Database-Code.txt"
This application requires a JDBC connection is made to your MySQL database. 
JDBC urls, usernames, and passwords must be configured to match your system's MySQL database access specifications.
	
--To start the application enter a valid username and password.

--After the login information has been validated the Main Menu page is loaded. 
On the Main Menu page, you may select a customer or appointment and apply an Update or Delete service on the selection or click on the Add, Reports, or Exit buttons.

--If you do not wish to save changes you made to any form you may click the cancel button to exit the form page. 
Clicking the cancel button on all form pages will send you back to to the main menu.

--Appointment tables on the menu, update, and appointments pages can be filtered by month, week, or all.

--To exit the program you can either click the X on the top right corner of any page or you can click the Exit button on the Main Menu.

--The Main Menu has two tables. The top table has all appointments for all customers currently in the system while the bottom table shows all customers currently in the system. 
After selecting a customer or appointment the service button's of update or delete can be applied to the selected object. 
Clicking the Add button underneath the appointments table will cause the Main Menu page to close and the Appointments page to load. 
After clicking the Add button underneath the Customers table, the Main Menu page is closed and the Add Customer page is loaded. 
After clicking the Reports button the Main Menu page is closed and the Reports page is loaded.

--The Add Customer page provides a table with the option to view and select any other customer saved on the database for the services of Update and Delete. 
Filling out a completed form and clicking the save button saves the customer data to the database and updates the customers' table on the page with the newly saved data.

--The Update Customer page provides a table of the customer to update's appointments with the option to add, update, or delete a selected appointment. 
After selecting options in all combo boxes, filling out all text fields, and clicking the Save button the updated customer data will be saved to the database and the customer tables will be updated with the new customer data.

--The Appointments page provides a table with all appointments and a table with all the appointments of the customer whom is having an appointment added or updated. 
Users can add, update, or delete selected appointments on any table on the page. 
Only after a user has finished entering information in all text fields, selected options in all form combo boxes, and selected a date will the user be able to save the appointment data to the database by clicking the Save button. 
The times entered for the appointment are referenced for validation. If the times entered are validated, the appointment data will then be saved to the database.
Times selected for appointments must be after the current time of the appointment creation or update, and not intersect with any other scheduled appointment times.

--The Reports page provides three report tables:

---The appointments by month and type report is filtered and output according to the month selected from the combo box at the top right corner of that report's table. 
Month options loaded in the combo box are only months with appointments currently saved to the database. 
When a month is selected the table is loaded with the types of appointments and their totals for that month.

---The appointments by contact report is filtered and output according to the contact selected from the combo box at the top right corner of that report's table.
When a contact is selected the table is loaded with all appointments associated with that contact.


---The user productivity report provides a list of all users and the total number of times the user has successfully created, updated, and deleted a customer or appointment on the database through the application.
