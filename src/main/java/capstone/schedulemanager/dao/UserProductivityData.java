package capstone.schedulemanager.dao;

import capstone.schedulemanager.utilities.helpers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class UserProductivityData {

    public static int insertUserTrackData(int userId, String userName, String userReportTime, String userReportType){
        int rowsAffected = 0;

        String sql = "INSERT INTO user_tracker (user_id, user_name, user_time, user_report_type) " +
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

                int deleteinit = 0;
                if(userReportType.equals("DELETED")){

                    deletedMapTotal.put(userId,
                            deletedMapTotal.getOrDefault(userId, deleteinit) + 1);

                }

            }
        }
        catch (SQLException e){

            e.printStackTrace();
            helpers.databsConErrMsg();}

        }

    }
