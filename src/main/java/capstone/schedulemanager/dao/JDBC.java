package capstone.schedulemanager.dao;

import capstone.schedulemanager.utilities.helpers;

import java.sql.Connection;
import java.sql.DriverManager;

/** This class establishes a connection to the MySQL database.*/
public abstract class  JDBC {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = "TestUser"; // Username
    private static String password = "TestUser81"; // Password
    public static Connection connection;  // Connection Interface


    /** This method establishes a connetion to the database and stores the connection in a globally accessible variable.*/
    public static void openConnection() {

        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful");
        }
        catch(Exception e) {
            e.printStackTrace();
            helpers.databsConErrMsg();}
    }

    /** This method closes the connection to the MySQL database.*/
    public static void closeConnection() {

        try {
            connection.close();
        }
        catch(Exception e) {
            e.printStackTrace();
            helpers.databsConErrMsg();}
    }

    /** This method returns the connection object to the MySQL database.
     * @return A connection object to the database*/
    public static Connection getConnection(){
        return connection;
    }
}
