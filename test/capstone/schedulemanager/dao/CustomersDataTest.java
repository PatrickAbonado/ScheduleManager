package capstone.schedulemanager.dao;

import capstone.schedulemanager.model.Customers;
import capstone.schedulemanager.utilities.Conversion;
import capstone.schedulemanager.utilities.helpers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

public class CustomersDataTest {


    Connection connection;


    @Before
    public void startConnection() throws SQLException {

        connection = DriverManager.getConnection("jdbc:mysql://localhost/client_schedule?connectionTimeZone = SERVER", "TestUser", "TestUser81");
    }

    @After
    public void closeConnection() throws SQLException{
        connection.close();
    }

    public void insertCust (String custNam, String address, String postal,
                                  String phone, LocalDateTime createDate, String createBy,
                                  LocalDateTime lastUpdt, String lastUpdtBy, int divId, int custId) {

        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code," +
                "Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID," +
                "Customer_ID)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        int rowsAffected = 0;

        try{
            PreparedStatement ps = connection.prepareStatement(sql);
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

    }


    public ObservableList<Customers> getCustList() throws SQLException {

        ObservableList<Customers> customers = FXCollections.observableArrayList();

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/client_schedule?connectionTimeZone = SERVER", "TestUser", "TestUser81");

        Conversion cvrtCstrStmp = stamp -> stamp.toLocalDateTime().atZone(ZoneId.systemDefault()).toLocalDateTime();


        try{
            String sql = "SELECT * from customers";

            PreparedStatement ps = connection.prepareStatement(sql);

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

    @Test
    public void checkEmptyCustList() throws SQLException {

        ObservableList<Customers> customers = getCustList();

        assertFalse(customers.isEmpty());
    }

    @Test
    public void checkCustomersData() throws SQLException {

        ObservableList<Customers> customers = getCustList();

        String testName = "Michael Knight";

        assertEquals(testName, customers.get(0).getCustomerName());

    }

    @Test
    public void testCustomerInsert() throws SQLException {

        String custNam = "Peter Pan", address = "88 Banana Street", postal = "54123", phone = "874-521-9871",
                lastUpdtBy = "admin", createBy = "admin";

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime createDate = LocalDateTime.now().withNano(0);
        LocalDateTime lastUpdt = LocalDateTime.now().withNano(0);
        int divId = 15, custId = 2;

        insertCust (custNam, address, postal,
                phone, createDate, createBy,
                lastUpdt, lastUpdtBy, divId, custId);

        ObservableList<Customers> customers = getCustList();

        String testName = "Peter Pan";

        assertEquals(testName, customers.get(1).getCustomerName());

    }


}