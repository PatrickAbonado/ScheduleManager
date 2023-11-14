package capstone.schedulemanager.dao;

import capstone.schedulemanager.model.FirstLevelDivisions;
import capstone.schedulemanager.utilities.Conversion;
import capstone.schedulemanager.utilities.helpers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

/** This class provides methods to access and manipulate data from the First Level Divisions table on the mysql
 * database.*/
public abstract class FirstLevelDivisionsData {

    /** This method queries the database and creates an observable list from the First Level Division rows on the
     * First Level Division table.
     * LAMBDA EXPRESSION 1 (getConversion()): Converts Timestamp objects to Local Date Time objects. This expression
     * is used for code reuse and simplification.
     * @return states The list of states and provinces in an observable list*/
    public static ArrayList<capstone.schedulemanager.model.FirstLevelDivisions> getFstLvList() {

        /** Lambda declaration that converts Timestamp objects to LocalDateTime with the local zone ID time.*/
        Conversion cvrtFstLvlStmp = stamp -> stamp.toLocalDateTime().atZone(ZoneId.systemDefault()).toLocalDateTime();

        ArrayList<capstone.schedulemanager.model.FirstLevelDivisions> states = new ArrayList<>();

        try{
            String sql = "SELECT * from first_level_divisions";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int divId = rs.getInt("Division_ID");
                String divName = rs.getString("Division");
                Timestamp createDateStamp = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdateStamp = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int countId = rs.getInt("COUNTRY_ID");

                /** Implementation of lambda expression to convert table create date and last update timestamps to
                 * Local Date Time objects with the current zone time.*/
                LocalDateTime createDate = cvrtFstLvlStmp.getConversion(createDateStamp);
                LocalDateTime lastUpdate = cvrtFstLvlStmp.getConversion(lastUpdateStamp);

                capstone.schedulemanager.model.FirstLevelDivisions state = new capstone.schedulemanager.model.FirstLevelDivisions(divId, divName, createDate,
                        createdBy,lastUpdate,lastUpdatedBy, countId);

                states.add(state);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            helpers.databsConErrMsg();
        }

        return states;
    }

    /** This method returns the division name referenced by the input division ID.
     * @param divId The division ID to reference to find the name
     * @return divName The name of the division*/
    public static String getDivisionName(int divId){
        String divName = "";

            for(capstone.schedulemanager.model.FirstLevelDivisions division : getFstLvList()){
                if(division.getDivisionId() == divId){
                    divName = division.getDivisionName();
                }
            }

        return divName;
    }

    /** This method returns the division ID associated with the state or province name.
     * @param stePrvName The state or province name to reference
     * @return divId The division ID of the state or province referenced*/
    public static int getDivisionId(String stePrvName){
        int divId = 0;
        for (capstone.schedulemanager.model.FirstLevelDivisions division: getFstLvList()) {
            if(division.getDivisionName().equals(stePrvName)){
                divId = division.getDivisionId();
            }
        }

        return divId;
    }

}
