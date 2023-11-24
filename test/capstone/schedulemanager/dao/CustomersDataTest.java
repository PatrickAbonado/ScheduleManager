package capstone.schedulemanager.dao;

import capstone.schedulemanager.model.Customers;
import capstone.schedulemanager.utilities.Conversion;
import capstone.schedulemanager.utilities.helpers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Test;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.Assert.*;

public class CustomersDataTest {

    @Test
    public void getCustList() throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/client_schedule?connectionTimeZone = SERVER", "TestUser", "TestUser81");

        Conversion cvrtCstrStmp = stamp -> stamp.toLocalDateTime().atZone(ZoneId.systemDefault()).toLocalDateTime();

        ObservableList<Customers> customers = FXCollections.observableArrayList();

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

        String customerName = "Michael Knights";

        assertEquals(customerName, customers.get(0).getCustomerName());

        connection.close();

    }


}