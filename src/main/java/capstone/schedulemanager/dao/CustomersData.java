package capstone.schedulemanager.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import capstone.schedulemanager.model.Countries;
import capstone.schedulemanager.model.FirstLevelDivisions;
import capstone.schedulemanager.utilities.Conversion;
import capstone.schedulemanager.utilities.helpers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

/** This class provides methods to access and manipulate customers data from the customers table on the mysql database.*/
public abstract class CustomersData {

    /** This method acquires all rows from the customers table and converts them to objects stored on an observable
     * list.
     * LAMBDA EXPRESSION 1 (getConversion()): Converts Timestamp objects to Local Date Time objects. This expression
     * is used for code reuse and simplification.
     * @return customers The list of all customers data from the customers table on the mysql database*/
    public static ObservableList<capstone.schedulemanager.model.Customers> getCustList() {

        /** Lambda declaration used to convert Timestamp objects to Local Date Time objects with Zone ID time.*/
        Conversion cvrtCstrStmp = stamp -> stamp.toLocalDateTime().atZone(ZoneId.systemDefault()).toLocalDateTime();

        ObservableList<capstone.schedulemanager.model.Customers> customers = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from customers";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int custId = rs.getInt("Customer_ID");
                String custName = rs.getString("Customer_Name");
                String custAdrs = rs.getString("Address");
                String custPst = rs.getString("Postal_Code");
                String custPhn = rs.getString("Phone");
                Timestamp createDateStamp = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdateStamp = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int divisionId = rs.getInt("Division_ID");

                /** Implementation of lambda expression to convert table create date and last update timestamps to
                 * Local Date Time objects with the current zone time.*/
                LocalDateTime createDate = cvrtCstrStmp.getConversion(createDateStamp);
                LocalDateTime lastUpdate = cvrtCstrStmp.getConversion(lastUpdateStamp);

                capstone.schedulemanager.model.Customers customer = new capstone.schedulemanager.model.Customers(custId, custName, custAdrs,
                        custPst, custPhn, createDate, createdBy, lastUpdate,
                        lastUpdatedBy, divisionId);

                customers.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            helpers.databsConErrMsg();
        }

        return customers;
    }

    /** This method queries the database to insert a newly created customer from data acquired in the application
     * into the customers table on the mysql database. If the insert is successful it returns an integer number of the
     * rows affected.
     * @param divId The customer's division ID
     * @param lastUpdtBy The user that last updated the customer information
     * @param lastUpdt The date and time that the customer informationw as last updated
     * @param custId The customer's ID
     * @param address The customer's address
     * @param createBy The user that initially created the customer's data profile
     * @param createDate The date and time that the customer was initially created in the system
     * @param custNam The name of the customer
     * @param phone The customer's phone number
     * @param postal The customer's postal code
     * @return rowsAffected The affected row count
     */
    public static int insertCust (String custNam, String address, String postal,
                                  String phone, LocalDateTime createDate, String createBy,
                                  LocalDateTime lastUpdt, String lastUpdtBy, int divId, int custId) {

        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code," +
                "Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID," +
                "Customer_ID)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        int rowsAffected = 0;

        try{
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, custNam);
            ps.setString(2, address);
            ps.setString(3, postal);
            ps.setString(4, phone);
            ps.setTimestamp(5, Timestamp.valueOf(createDate));
            ps.setString(6,createBy);
            ps.setTimestamp(7, Timestamp.valueOf(lastUpdt));
            ps.setString(8, lastUpdtBy);
            ps.setInt(9,divId);
            ps.setInt(10, custId);

            rowsAffected = ps.executeUpdate();
        }
        catch(SQLException se){
            se.printStackTrace();
            helpers.databsConErrMsg();
        }


        return rowsAffected;
    }

    /** This method returns the customer's name that matches the input customer ID.
     * @param custId The customer ID to reference
     * @return name The name of the customer*/
    public static String getCustomerName(int custId){
        String name = "";

        ObservableList<capstone.schedulemanager.model.Customers> customerList = getCustList();

        for(int i = 0; i < customerList.size(); ++i){
            if(customerList.get(i).getCustomerId() == custId){
                name = customerList.get(i).getCustomerName();
            }
        }

        return name;
    }

    /** This method queries the database to update an existing customer on the customer table with data acquired from
     * the application. If the update is successful the method returns a number of the rows affected by the query.
     * @param postal The updated postal code
     * @param phone The updated phone number
     * @param address The updated address
     * @param lastUpdt The updated last update date and time
     * @param divId The updated division ID
     * @param lastUpdtBy The updated last user to update
     * @param customerId The customer ID of the date being updated
     * @param custName The name of the customer being updated
     * @return rowsAffected The affected row count*/
    public static int updateCustomer(int customerId, String custName, String address,
                                        String postal, String phone, LocalDateTime lastUpdt,
                                     String lastUpdtBy, int divId){
        int rowsAffected = 0;

        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, " +
                "Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";

        PreparedStatement ps = null;
        try {
            ps = JDBC.connection.prepareStatement(sql);

            ps.setString(1, custName);
            ps.setString(2, address);
            ps.setString(3, postal);
            ps.setString(4, phone);
            ps.setTimestamp(5, Timestamp.valueOf(lastUpdt));
            ps.setString(6,lastUpdtBy);
            ps.setInt(7, divId);
            ps.setInt(8, customerId);

            rowsAffected = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            helpers.databsConErrMsg();
        }

        return rowsAffected;

    }

    /** This method queries the database to delete a customer whose customer ID matches the inputted customer ID.
     * If successful this method returns a row count of the rows affected by the delete.
     * @param customerId The customer's ID to delete
     * @return rowsAffected The row count of rows affected by the delete*/
    public static int deleteCustomer(int customerId){
        int rowsAffected = 0;

        String sql = "DELETE FROM customers WHERE Customer_ID = ?";

        PreparedStatement ps = null;

        try{
            ps = JDBC.connection.prepareStatement(sql);

            ps.setInt(1,customerId);

            rowsAffected = ps.executeUpdate();
        }
        catch (SQLException s){
            s.printStackTrace();
            helpers.databsConErrMsg();
        }

        return rowsAffected;
    }

    /** This method returns the number of the next customer ID to be added the customers table.
     * @return lastNum + 1 The highest customer ID number plus 1*/
    public static int getNextCustId(){
        int lastNum = 0;
        for (int i = 0; i < getCustList().size(); ++i) {
            if(getCustList().get(i).getCustomerId() > lastNum){
                lastNum = getCustList().get(i).getCustomerId();
            }
        }

        return lastNum + 1;
    }

    /** This method creates and returns an observable list of all customers in the database with the names of their
     * associated divisions and countries.
     * @return The list of customers*/
    public static ObservableList<capstone.schedulemanager.model.CustsWthNms> getCustWithNmLst(){

        int customerId = 0, divisionId=0, countryId=0;
        String customerName = "", address = "", postalCode = "", phoneNum="", createdBy="", lastUpdatedBy="",
                divisionName="", countryName="";
        LocalDateTime createDate = null, lastUpdate=null;

        ObservableList<capstone.schedulemanager.model.CustsWthNms> custWithNmsList = FXCollections.observableArrayList();
        ArrayList<FirstLevelDivisions> divisionsList = FirstLevelDivisionsData.getFstLvList();
        ArrayList<Countries> countries = CountriesData.getCntryList();


        ObservableList<capstone.schedulemanager.model.Customers> customerList = getCustList();

        for (int i = 0; i < customerList.size();++i){
            capstone.schedulemanager.model.Customers customer = CustomersData.getCustList().get(i);

            customerId = customer.getCustomerId();
            customerName = customer.getCustomerName();
            address = customer.getAddress();
            postalCode = customer.getPostalCode();
            phoneNum = customer.getPhoneNum();
            createDate = customer.getCreateDate();
            createdBy = customer.getCreatedBy();
            lastUpdate = customer.getLastUpdate();
            lastUpdatedBy = customer.getLastUpdatedBy();
            divisionId = customer.getDivisionId();

            for(capstone.schedulemanager.model.FirstLevelDivisions division : divisionsList){
                if(divisionId == division.getDivisionId()){
                    divisionName = division.getDivisionName();
                    countryId = division.getCountryId();
                }
            }

            for(capstone.schedulemanager.model.Countries country : countries){
                if(country.getCountryId() == countryId){
                    countryName = country.getCountryName();
                }
            }

            capstone.schedulemanager.model.CustsWthNms custsWthNms =
                    new capstone.schedulemanager.model.CustsWthNms(customerId, customerName, address,  postalCode, phoneNum,
                    createDate, createdBy, lastUpdate, lastUpdatedBy, divisionId, divisionName, countryName, countryId);

            custWithNmsList.add(custsWthNms);
        }

        return custWithNmsList;
    }

}


