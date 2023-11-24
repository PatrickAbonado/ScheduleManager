package capstone.schedulemanager.dao;


import capstone.schedulemanager.utilities.Conversion;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class UsersDataTest {

    private Connection connection;

    @Before
    public void setUp() throws SQLException {
        // Set up a connection to your test database
        connection = DriverManager.getConnection("jdbc:mysql://localhost/client_schedule?connectionTimeZone = SERVER", "TestUser", "TestUser81");
        // Initialize test data in the database
        getUsrsList();
    }

    @After
    public void tearDown() throws SQLException {
        // Clean up and close the connection
        connection.close();
    }

    @Test
    public void getUsrsList() throws SQLException {

        ArrayList<capstone.schedulemanager.model.Users> users = new ArrayList<>();
        Conversion cvrtUsrsStmp = stamp -> stamp.toLocalDateTime().atZone(ZoneId.systemDefault()).toLocalDateTime();


            String sql = "SELECT * from users";

            PreparedStatement ps = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int usrId = rs.getInt("User_ID");
                String usrName = rs.getString("User_Name");
                String password = rs.getString("Password");
                Timestamp createDateStamp = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdateStamp = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");

                LocalDateTime createDate = cvrtUsrsStmp.getConversion(createDateStamp);
                LocalDateTime lastUpdate = cvrtUsrsStmp.getConversion(lastUpdateStamp);

                capstone.schedulemanager.model.Users user = new capstone.schedulemanager.model.Users(usrId, usrName, password,
                        createDate, createdBy, lastUpdate,
                        lastUpdatedBy);

                users.add(user);

            }

        String username = "test-invalid";

        assertEquals(username, users.get(0).getUserName());

    }

}