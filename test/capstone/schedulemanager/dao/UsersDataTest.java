package capstone.schedulemanager.dao;


import capstone.schedulemanager.utilities.Conversion;

import org.junit.Test;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class UsersDataTest {

    @Test
    public void getUsrsList() throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/client_schedule?connectionTimeZone = SERVER", "TestUser", "TestUser81");


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

        String username = "test";

        assertEquals(username, users.get(0).getUserName());

        connection.close();

    }

}