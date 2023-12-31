package capstone.schedulemanager.controller;

import capstone.schedulemanager.model.Appointments;
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

public class MainMenuControllerTest {

    @Test
    public void onMainAptsSrchButClick() throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/client_schedule?connectionTimeZone = SERVER",
                "TestUser", "TestUser81");

        ObservableList<Appointments> appointments = FXCollections.observableArrayList();

        Conversion cvrtAptStmp = stamp -> stamp.toLocalDateTime().atZone(ZoneId.systemDefault()).toLocalDateTime();

        try{
            String sql = "SELECT * from appointments";

            PreparedStatement ps = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                capstone.schedulemanager.model.Appointments appointment = new capstone.schedulemanager.model.Appointments(appointmentId, title, description,
                        location, type, cvrtAptStmp.getConversion(start), cvrtAptStmp.getConversion(end),
                        cvrtAptStmp.getConversion(createDate), createdBy, cvrtAptStmp.getConversion(lastUpdate),
                        lastUpdatedBy, customerId, userId, contactId);

                appointments.add(appointment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            helpers.databsConErrMsg();}


        Conversion cvrtCstrStmp = stamp -> stamp.toLocalDateTime().atZone(ZoneId.systemDefault()).toLocalDateTime();

        ObservableList<capstone.schedulemanager.model.Customers> customers = FXCollections.observableArrayList();

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


        String searchText = "rep";

        ObservableList<Appointments> resultList = FXCollections.observableArrayList();

        String customerName = "";

        for(Appointments appointment : appointments){

            for (Customers customer : customers){

                if(customer.getCustomerId() == appointment.getCustomerId()){

                    customerName = customer.getCustomerName();
                }
            }

            if(customerName.contains(searchText) ||
                    appointment.getStartDateAndTime().toString().contains(searchText) ||
                    appointment.getEndDateAndTime().toString().contains(searchText)){
                resultList.add(appointment);
            }
        }


        int counter = resultList.size();

        for (Appointments appointment : resultList){

            for (Customers customer : customers){

                if(customer.getCustomerId() == appointment.getCustomerId()){

                    customerName = customer.getCustomerName();
                }
            }

            if(customerName.contains(searchText) || appointment.getStartDateAndTime().toString().contains(searchText)
            || appointment.getEndDateAndTime().toString().contains(searchText)){
                --counter;
            }
        }


        assertEquals(0, counter);

        connection.close();

    }

    @Test
    public void onMainCustSrchButClick() throws SQLException {

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

        String searchText = "kn";

        ObservableList<capstone.schedulemanager.model.Customers> resultList = FXCollections.observableArrayList();

        for (int i = 0; i < customers.size(); ++i) {

            if (customers.get(i).getCustomerName().toLowerCase().contains(searchText)
                    || customers.get(i).getPhoneNum().contains(searchText)) {
                resultList.add(customers.get(i));

            }
        }

        int counter = resultList.size();



        for(Customers customer : resultList){

            if (customer.getCustomerName().toLowerCase().contains(searchText)
                    || customer.getPhoneNum().contains(searchText)) {
                --counter;
            }
        }



        assertEquals(0, counter);

        connection.close();

    }
}