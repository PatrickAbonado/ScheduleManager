package capstone.schedulemanager.dao;

import capstone.schedulemanager.utilities.Conversion;
import capstone.schedulemanager.utilities.helpers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

/** This class provides methods to access and maninpulate data on the Users table on the mysql database.*/
public abstract class UsersData {

    private static int usrDatUsrId;

    /** This method queries the database and creates an observable list from the rows of users on the
     * Users table.
     * LAMBDA EXPRESSION 1 (getConversion()): Converts Timestamp objects to Local Date Time objects. This expression
     * is used for code reuse and simplification.
     * @return users The list of users on the users table in an observable list*/
    public static ArrayList<capstone.schedulemanager.model.Users> getUsrsList() {

        /** Lambda declaration that converts Timestamp objects to LocalDateTime with the local zone ID time.*/
        Conversion cvrtUsrsStmp = stamp -> stamp.toLocalDateTime().atZone(ZoneId.systemDefault()).toLocalDateTime();

        ArrayList<capstone.schedulemanager.model.Users> users = new ArrayList<>();

        try{
            String sql = "SELECT * from users";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int usrId = rs.getInt("User_ID");
                String usrName = rs.getString("User_Name");
                String password = rs.getString("Password");
                Timestamp createDateStamp = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdateStamp = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");

                /** Implementation of lambda expression to convert table create date and last update timestamps to
                 * Local Date Time objects with the current zone time.*/
                LocalDateTime createDate = cvrtUsrsStmp.getConversion(createDateStamp);
                LocalDateTime lastUpdate = cvrtUsrsStmp.getConversion(lastUpdateStamp);

                capstone.schedulemanager.model.Users user = new capstone.schedulemanager.model.Users(usrId, usrName, password,
                        createDate, createdBy, lastUpdate,
                        lastUpdatedBy);

                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            helpers.databsConErrMsg();}

        return users;
    }

    /**
     * @param userId The user ID to set
     * */
    public static void setUsrDatUsrId(int userId){
        usrDatUsrId = userId;
    }

    /**
     * @return The user ID to return
     * */
    public static int getUsrDatUsrId(){
        return usrDatUsrId;
    }

}