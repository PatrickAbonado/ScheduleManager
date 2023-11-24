package capstone.schedulemanager.dao;

import capstone.schedulemanager.model.Appointments;
import capstone.schedulemanager.utilities.Conversion;
import capstone.schedulemanager.utilities.helpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.time.ZoneId;

import static org.junit.Assert.*;

public class AppointmentsDataTest {

    ObservableList<Appointments> appointments = FXCollections.observableArrayList();

    @Before
    public void getAptList() throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/client_schedule?connectionTimeZone = SERVER", "TestUser", "TestUser81");

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

    }

    @Test
    public void checkEmptyAptList(){

        assertFalse(appointments.isEmpty());
    }

    @Test
    public void checkAptData(){

        String testType = "repairz";

        assertEquals(testType, appointments.get(0).getType());

    }


}