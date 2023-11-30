package capstone.schedulemanager.dao;

import capstone.schedulemanager.model.Users;
import capstone.schedulemanager.utilities.Conversion;
import capstone.schedulemanager.utilities.helpers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/** This class provides methods to access and maninpulate data on the Users table on the mysql database.*/
public abstract class UsersData {

    static ResourceBundle rb = ResourceBundle.getBundle("Languages", Locale.getDefault());


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

    public static int insertUserTrackData(int userId, String userName, String userReportTime, String userReportType){
        int rowsAffected = 0;

        String sql = "INSERT INTO user_tracker (user_id, user_name, user_report_time, user_report_type) " +
                "VALUES (?, ?, ?, ?)";

        PreparedStatement ps = null;
        try {
            ps = JDBC.connection.prepareStatement(sql);


            ps.setInt(1,userId);
            ps.setString(2, userName);
            ps.setString(3, userReportTime);
            ps.setString(4, userReportType);
            rowsAffected = ps.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
            helpers.databsConErrMsg();
        }

        return rowsAffected;

    }
    public static void updateUserProductivityCounts(Map<Integer, Integer> createdMapTotal,
                                                    Map<Integer, Integer> updatedMapTotal,
                                                    Map<Integer, Integer> deletedMapTotal){


        try {
            String sql = "SELECT * from user_tracker";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                int userId = rs.getInt("user_id");
                String userReportType = rs.getString("user_report_type");


                int createdInit = 0;
                if(userReportType.equals("CREATED")){

                    createdMapTotal.put(userId,
                            createdMapTotal.getOrDefault(userId, createdInit) + 1);

                }

                int updateInit = 0;
                if(userReportType.equals("UPDATED")){

                    updatedMapTotal.put(userId,
                            updatedMapTotal.getOrDefault(userId, updateInit) + 1);

                }

                int deleteInit = 0;
                if(userReportType.equals("DELETED")){

                    deletedMapTotal.put(userId,
                            deletedMapTotal.getOrDefault(userId, deleteInit) + 1);

                }

            }
        }
        catch (SQLException e){

            e.printStackTrace();
            helpers.databsConErrMsg();}

    }

    public static void createUserDeleteReport(){


        ArrayList<Users> users = UsersData.getUsrsList();
        String userName = "";
        int userId = UsersData.getUsrDatUsrId();
        for(Users user : users){

            if(user.getUserId() == userId){
                userName = user.getUserName();
            }

        }

        LocalDateTime deleteDateAndTime = LocalDateTime.now().withNano(0);
        String dateAndTimeDeleted = deleteDateAndTime.toString();
        String reportType = rb.getString("userProdReportDeleted");

        insertUserTrackData(userId,userName,dateAndTimeDeleted,
                reportType);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("device-user-productivity.txt",true))) {
            bw.write(userId + " " + userName + " " + deleteDateAndTime + " " + reportType + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void createUserCreateReport(){


        ArrayList<Users> users = UsersData.getUsrsList();
        String userName = "";
        int userId = UsersData.getUsrDatUsrId();
        for(Users user : users){

            if(user.getUserId() == userId){
                userName = user.getUserName();
            }
        }
        LocalDateTime createDate = LocalDateTime.now().withNano(0);
        String dateAndTimeCreated = createDate.toString();
        String reportType = rb.getString("userProdReportCreated");

        insertUserTrackData(userId,userName,dateAndTimeCreated,
                reportType);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("device-user-productivity.txt",true))) {
            bw.write(userId + " " + userName + " " + createDate + " " + reportType + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createUserUpdateReport(){

        ArrayList<Users> users = UsersData.getUsrsList();
        String userName = "";
        int userId = UsersData.getUsrDatUsrId();
        for(Users user : users){
            if(user.getUserId() == userId){
                userName = user.getUserName();
            }
        }

        LocalDateTime lastUpdt = LocalDateTime.now().withNano(0);
        String lastUpdtString = lastUpdt.toString();
        String reportType = rb.getString("userProdReportUpdate");

        insertUserTrackData(userId,userName,lastUpdtString,
                reportType);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("device-user-productivity.txt",true))) {
            bw.write(userId + " " + userName + " " + lastUpdt + " " + reportType + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}