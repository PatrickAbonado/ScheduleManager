package capstone.schedulemanager.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import capstone.schedulemanager.utilities.Conversion;
import capstone.schedulemanager.utilities.helpers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

/** This class provides the methods for access and manipulation of the appointments table data in the mysql database.*/
public abstract class AppointmentsData {

    /** This method returns the data from the appointments table form the database as an observable array list.
     * The database is queried for the column information from the appointments table. The data returned from the
     * database is then stored as objects in an observable array list. Mysql Timestamp types are first converted to
     * Local Date Time objects then Zone Date Time objects then back to Local Date Time objects to create instances of
     * real time Local Date Time objects.
     * LAMBDA EXPRESSION 1 (getConversion()): Converts Timestamp objects to Local Date Time objects. This expression is used for code
     * reuse and simplification.
     * @return appointments A list of the appointments and their data on the appointments table*/
    public static ObservableList<capstone.schedulemanager.model.Appointments> getAptList(){

        ObservableList<capstone.schedulemanager.model.Appointments> appointments = FXCollections.observableArrayList();

        /** Lambda declaration to convert sql Timestamp objects into Local Date Time objects as real time instances.*/
        Conversion cvrtAptStmp = stamp -> stamp.toLocalDateTime().atZone(ZoneId.systemDefault()).toLocalDateTime();

        try{
            String sql = "SELECT * from appointments";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

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

                /** Lambda used to convert Timestamp objects to Local Date Time objects.*/
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

    /** This method inserts appointment data into the appointments table of the database. This method takes validated
     * data acquired from the appointments page and inserts the data into the appointments table with the next highest
     * appointment ID.
     * @param appointmentId The appointment ID
     * @param contactId The contact ID
     * @param creatDt The created date
     * @param createdBy The created by user
     * @param custId The customer ID
     * @param description The description of the appointment
     * @param endDateAndTime The end date and time
     * @param lastUpdt The last updated date and time
     * @param location The location of the appointment
     * @param lastUpdtBy The user that last updated the appointment
     * @param title The title of the appointment
     * @param type The type of the appoinment
     * @param startDateAndTime The start and date time of the appoinment
     * @param userId  The user ID of creator/updater of the appointment
     * @return the amount of rows affected*/
    public static int insertAppt (int custId, String title, String description, String location,
                                  String type, Timestamp startDateAndTime, Timestamp endDateAndTime,
                                  Timestamp creatDt, String createdBy, Timestamp lastUpdt, String lastUpdtBy,
                                  int userId, int contactId, int appointmentId)  {
        int rowsAffected = 0;

        String sql = "INSERT INTO appointments (Customer_ID, Title, Description, Location," +
                "Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, " +
                "User_ID, Contact_ID, Appointment_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = null;
        try {
            ps = JDBC.connection.prepareStatement(sql);

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

        return rowsAffected;
    }

    /** This method updates the data of an existing appointment. This method takes validated data from the appointments
     * page and updates the appointment's data with the matching appointment ID on the appointments table in the
     * database.
     * @param aptId The appointment ID
     * @param contactId The contact ID
     * @param description The description of the appointment
     * @param endDateAndTime The end date and time
     * @param lastUpdt The last updated date and time
     * @param location The location of the appointment
     * @param lastUpdtBy The user that last updated the appointment
     * @param title The title of the appointment
     * @param type The type of the appoinment
     * @param startDateAndTime The start and date time of the appoinment
     * @param userId The user ID of creator/updater of the appointment
     * @param custId The customer ID
     * @return The number of rows affected*/
    public static int updateAppointment(String title, String description, String location,
                                        String type, Timestamp startDateAndTime, Timestamp endDateAndTime, Timestamp lastUpdt,
                                        String lastUpdtBy, int userId, int contactId, int aptId, int custId){
        int rowsAffected = 0;

        String sql = "UPDATE appointments " +
                "SET Title = ?, Description = ?, Location = ?, Type = ?," +
                "Start = ?, End = ?, Last_Update = ?," +
                "Last_Updated_By = ?, User_ID = ?, Contact_ID = ?,  Customer_ID = ? " +
                "WHERE Appointment_ID = ?";

        PreparedStatement ps = null;
        try {
            ps = JDBC.connection.prepareStatement(sql);

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, startDateAndTime);
            ps.setTimestamp(6, endDateAndTime);
            ps.setTimestamp(7, lastUpdt);
            ps.setString(8, lastUpdtBy);
            ps.setInt(9,userId);
            ps.setInt(10,contactId);
            ps.setInt(11, custId);
            ps.setInt(12, aptId);

            rowsAffected = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            helpers.databsConErrMsg();
        }

        return rowsAffected;

    }

    /** This method gets the next appointment ID after the highest appointment ID number on the appointments table in
     * the database.
     * @return lastNum + 1 The highest appointment ID number plus 1.*/
    public static int getNextAptId(){
        int lastNum = 0;

        ObservableList<capstone.schedulemanager.model.Appointments> appointments = getAptList();
        for (int i = 0; i < appointments.size(); ++i) {
            if(appointments.get(i).getAppointmentId() > lastNum){
                lastNum = appointments.get(i).getAppointmentId();
            }
        }

        return lastNum + 1;
    }

    /** This method deletes and appointment and returns an integer indicating whether the delete process was successful.
     * This method takes an appointment ID and sends a mysql query to the database requesting the delete of an
     * appointment that possesses the appointment ID. If the delete was successful the rows affected value is
     * incremented.
     * @param aptId The appointment ID to delete
     * @return rowsAffected The number of rows affected by the delete*/
    public static int deleteAppointment(int aptId){
        int rowsAffected = 0;

        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";

        PreparedStatement ps = null;

        try{
            ps = JDBC.connection.prepareStatement(sql);

            ps.setInt(1,aptId);

            rowsAffected = ps.executeUpdate();
        }
        catch (SQLException s){
            s.printStackTrace();
            helpers.databsConErrMsg();
        }

        return rowsAffected;

    }

    /** This method creates an observable list of all appointments on the database with the names of both the customer
     * and contact whom are associated with the appointment.
     * @return aptsWithNameApts the observable list with of appointments with the customer and contact names*/
    public static ObservableList<capstone.schedulemanager.model.AptWithName> getAptsWtNmsList(){

        ObservableList<capstone.schedulemanager.model.AptWithName> aptsWithNameApts = FXCollections.observableArrayList();

        ObservableList<capstone.schedulemanager.model.Appointments> aptList = getAptList();

        for(int i = 0; i < aptList.size(); ++i){
            int aptId = aptList.get(i).getAppointmentId();
            String title = aptList.get(i).getTitle();
            String dscrp = aptList.get(i).getDescription();
            String loc = aptList.get(i).getLocation();
            String type = aptList.get(i).getType();
            LocalDateTime startDateAndTime = aptList.get(i).getStartDateAndTime();
            LocalDateTime endDateAndTime = aptList.get(i).getEndDateAndTime();
            LocalDateTime createDate = aptList.get(i).getCreateDate();
            String crtBy = aptList.get(i).getCreatedBy();
            LocalDateTime lastUpdate = aptList.get(i).getLastUpdate();
            String lstUpdtBy = aptList.get(i).getLastUpdateBy();
            int cstId = aptList.get(i).getCustomerId();
            int cntId = aptList.get(i).getContactId();
            int usrId = aptList.get(i).getUserId();
            String custName = CustomersData.getCustomerName(cstId);
            String cntName = ContactsData.getContactName(cntId);
            aptsWithNameApts.add(new capstone.schedulemanager.model.AptWithName(aptId, title, dscrp, loc, type,
                    startDateAndTime, endDateAndTime, createDate, crtBy, lastUpdate, lstUpdtBy, cstId,
                    usrId, cntId, custName, cntName));
        }

        return aptsWithNameApts;
    }

}

