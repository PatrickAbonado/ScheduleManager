package capstone.schedulemanager.dao;

import capstone.schedulemanager.model.Appointments;
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

public class AppointmentsDataTest {



    Connection connection;

    @Before
    public void createConnection() throws SQLException {

        connection = DriverManager.getConnection("jdbc:mysql://localhost/client_schedule?connectionTimeZone = SERVER", "TestUser", "TestUser81");
    }


    @After
    public void closeConnection() throws SQLException {

        connection.close();
    }




    public void insertAppointment(int custId, String title, String description, String location,
                                  String type, Timestamp startDateAndTime, Timestamp endDateAndTime,
                                  Timestamp creatDt, String createdBy, Timestamp lastUpdt, String lastUpdtBy,
                                  int userId, int contactId, int appointmentId){

            int rowsAffected = 0;

            String sql = "INSERT INTO appointments (Customer_ID, Title, Description, Location," +
                    "Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, " +
                    "User_ID, Contact_ID, Appointment_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = null;
            try {
                ps = connection.prepareStatement(sql);

                ps.setInt(1,custId);
                ps.setString(2, title);
                ps.setString(3, description);
                ps.setString(4, location);
                ps.setString(5, type);
                ps.setTimestamp(6, startDateAndTime);
                ps.setTimestamp(7, endDateAndTime);
                ps.setTimestamp(8, creatDt);
                ps.setString(9, createdBy);
                ps.setTimestamp(10, lastUpdt);
                ps.setString(11, lastUpdtBy);
                ps.setInt(12, userId);
                ps.setInt(13, contactId);
                ps.setInt(14, appointmentId);
                rowsAffected = ps.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
                helpers.databsConErrMsg();
            }

    }



    public ObservableList<Appointments> getAptList() throws SQLException {

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


        return appointments;

    }




    @Test
    public void checkEmptyAptList() throws SQLException {

        ObservableList<Appointments> appointments = getAptList();

        assertFalse(appointments.isEmpty());
    }

    @Test
    public void checkAptData() throws SQLException {

        ObservableList<Appointments> appointments = getAptList();

        String testType = "repair";

        assertEquals(testType, appointments.get(0).getType());

    }

    @Test
    public void checkInsertAppointmentData() throws SQLException {

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        int custId = 1;
        String title = "remodel room";
        String description = "remove room wall to enlargen living room space";
        String location = "34 Heath Street";
        String type = "remodel";
        Timestamp startDateAndTime = Timestamp.valueOf(LocalDateTime.parse("2023-12-10 10:00:00", format));
        Timestamp endDateAndTime = Timestamp.valueOf(LocalDateTime.parse("2023-12-10 11:00:00", format));
        Timestamp creatDt = Timestamp.valueOf(LocalDateTime.now().withNano(0));
        String createdBy = "test";
        Timestamp lastUpdt = Timestamp.valueOf(LocalDateTime.now().withNano(0));
        String lastUpdtBy = "test";
        int userId = 1;
        int contactId = 1;
        int appointmentId = 2;

        insertAppointment(custId, title, description, location, type, startDateAndTime, endDateAndTime, creatDt,
                createdBy, lastUpdt, lastUpdtBy, userId, contactId, appointmentId);


        ObservableList<Appointments> appointments = getAptList();

        String testType = "remodel";

        assertEquals(testType, appointments.get(1).getType());

    }


}