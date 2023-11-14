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

/** This class accesses and manipulates data from the countries table on the mysql database.*/
public abstract class CountriesData {

    /** This method stores the data of each country on the list as objects on an array list.
     * LAMBDA EXPRESSION declaration of the conversion of Timestamp objects to LocalDateTime objects
     * @return countries The list of countries and their data from the database*/
    public static ArrayList<capstone.schedulemanager.model.Countries> getCntryList(){


        Conversion cvrtCntryStmp = stamp -> stamp.toLocalDateTime().atZone(ZoneId.systemDefault()).toLocalDateTime();

        ArrayList<capstone.schedulemanager.model.Countries> countries = new ArrayList<>();

        try{
            String sql = "SELECT * from countries";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Timestamp createDateStamp = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdateStamp = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");

                /** Use of Lambda to convert Timestamp database objects to Local Date Time objects.*/
                LocalDateTime createDate = cvrtCntryStmp.getConversion(createDateStamp);
                LocalDateTime lastUpdate = cvrtCntryStmp.getConversion(lastUpdateStamp);

                capstone.schedulemanager.model.Countries country =
                        new capstone.schedulemanager.model.Countries(countryId, countryName, createDate,
                        createdBy,lastUpdate,lastUpdatedBy);

                countries.add(country);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            helpers.databsConErrMsg();
        }

        return countries;
    }

    /** This method gets the country name of a division ID.
     * @param divId The division ID whose country name is requested
     * @return countryName The name of the country where the division ID belongs*/
    public static String getCountryName(int divId) {
        String countryName = "";
        int countryId = 0;

            for (capstone.schedulemanager.model.FirstLevelDivisions division : FirstLevelDivisionsData.getFstLvList()){
                if (divId == division.getDivisionId()){
                    countryId = division.getCountryId();
                }
            }
            for(capstone.schedulemanager.model.Countries country : getCntryList()){
                if(countryId == country.getCountryId()){
                    countryName = country.getCountryName();
                }
            }

        return countryName;
    }

    /** This method gets the country ID of a country name.
     * @param name The name of the country whose ID is requested.
     * @return id The ID of the country whose name was provided*/
    public static int getCountryId(String name){
        int id = 0;
        for (capstone.schedulemanager.model.Countries country : CountriesData.getCntryList()){
            if(country.getCountryName().equals(name)){
                id = country.getCountryId();
            }
        }
        return id;
    }
}
