package capstone.schedulemanager.utilities;

import capstone.schedulemanager.model.Appointments;
import capstone.schedulemanager.model.Customers;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

/** This class provides methods to help access, organize, and output data features in the application.*/
public abstract class helpers {

    static ResourceBundle rb = ResourceBundle.getBundle("Languages", Locale.getDefault());

    /** This method creates a LocalDateTime object of the date and time of the start of daylight savings time.
     * @param timeZone The local time zone
     * @param year The year for the date of the start of daylight savings
     * @return dateTime.toLocalDateTime() The LocalDateTime object of the start of daylight savings time*/
    private static LocalDateTime getDaylightSavingStart(int year, ZoneId timeZone) {

        Month month = Month.MARCH;
        int dayOfMonth = 12;

        ZonedDateTime dateTime = ZonedDateTime.of(year, month.getValue(), dayOfMonth, 2, 0, 0, 0, timeZone);

        return dateTime.toLocalDateTime();
    }

    /** This method creates a LocalDateTime object of the date and time of the end of daylight savings time.
     * @param timeZone The local time zone
     * @param year The year for the date of the end of daylight savings
     * @return dateTime.toLocalDateTime() The LocalDateTime object of the end of daylight savings time*/
    private static LocalDateTime getDaylightSavingEnd(int year, ZoneId timeZone) {
        Month month = Month.NOVEMBER;
        int dayOfMonth = 5;

        ZonedDateTime dateTime = ZonedDateTime.of(year, month.getValue(), dayOfMonth, 2, 0, 0, 0, timeZone);

        return dateTime.toLocalDateTime();
    }

    /** This method is used to return the start of business hours in UTC time according to the local zone ID
     * @param zid The local zone ID
     * @return hourStart The business start hour*/
    public static int getZoneStrtHrConv(ZoneId zid){
        int hourStart = 8;


        if(zid.equals(ZoneId.of("Europe/London"))){
            hourStart = 13;
        }

        if(zid.equals(ZoneId.of("America/Phoenix"))){
            LocalDateTime dstStart = getDaylightSavingStart(2023, zid);
            LocalDateTime dstEnd = getDaylightSavingEnd(2023, zid);
            LocalDateTime nxtYrDstStart = getDaylightSavingStart(2024, zid);
            LocalDateTime now = LocalDateTime.now();

            if((now.isAfter(dstStart) || now.isEqual(dstStart)) && now.isBefore(dstEnd)){
                hourStart = 5;
            }
            if((now.isAfter(dstEnd) || now.isEqual(dstEnd)) && now.isBefore(nxtYrDstStart)){
                hourStart = 6;
            }
        }

        if(zid.equals(ZoneId.of("America/Halifax"))){
            hourStart = 9;
        }

        if(zid.equals(ZoneId.of("America/Chicago")) || zid.equals(ZoneId.of("America/Winnipeg"))){
            hourStart = 7;
        }
        if(zid.equals(ZoneId.of("America/Denver")) || zid.equals(ZoneId.of("America/Edmonton"))){
            hourStart = 6;
        }
        if(zid.equals(ZoneId.of("America/Los_Angeles")) || zid.equals(ZoneId.of("America/Vancouver"))){
            hourStart = 5;
        }

        if(zid.equals(ZoneId.of("Pacific/Honolulu"))){
            hourStart = 2;
        }

        return hourStart;
    }

    /** This method is used to return the end of business hours in UTC time according to the local zone ID
     * @param zid The local zone ID
     * @return hourEnd The business end hour*/
    public static int getZoneEndHrConv(ZoneId zid){
        int hourEnd = 22;

        if(zid.equals(ZoneId.of("Europe/London"))){
            hourEnd = 3;
        }

        if(zid.equals(ZoneId.of("America/Phoenix"))){
            LocalDateTime dstStart = getDaylightSavingStart(2023, zid);
            LocalDateTime dstEnd = getDaylightSavingEnd(2023, zid);
            LocalDateTime nxtYrDstStart = getDaylightSavingStart(2024, zid);
            LocalDateTime now = LocalDateTime.now();

            if((now.isAfter(dstStart) || now.isEqual(dstStart)) && now.isBefore(dstEnd)){
                hourEnd = 19;
            }
            if((now.isAfter(dstEnd) || now.isEqual(dstEnd)) && now.isBefore(nxtYrDstStart)){
                hourEnd = 20;
            }
        }

        if(zid.equals(ZoneId.of("America/Halifax"))){
            hourEnd = 23;
        }

        if(zid.equals(ZoneId.of("America/Chicago")) || zid.equals(ZoneId.of("America/Winnipeg"))){
            hourEnd = 21;
        }
        if(zid.equals(ZoneId.of("America/Denver")) || zid.equals(ZoneId.of("America/Edmonton"))){
            hourEnd = 20;
        }
        if(zid.equals(ZoneId.of("America/Los_Angeles")) || zid.equals(ZoneId.of("America/Vancouver"))){
            hourEnd = 19;
        }
        if(zid.equals(ZoneId.of("Pacific/Honolulu"))){
            hourEnd = 16;
        }

        return hourEnd;
    }

    /** This method takes an empty hashmap and adds start and end time key value pairs to the map. The start and end
     * times on the map are compared to input start and end times and returns true if any conditions are counted.
     * @param end The end time to be compared
     * @param map The empty map to be filled
     * @param start The start time to be compared
     * @return The boolean value for whether invalid*/
    public static boolean isInvalidTimeDate(Map<LocalDateTime, LocalDateTime> map, LocalDateTime start, LocalDateTime end){
        boolean isInvalid = false;
        int counter = 0;

        try{
            for (Map.Entry<LocalDateTime, LocalDateTime> time : map.entrySet()) {
                LocalDateTime startTime = time.getKey();
                LocalDateTime endTime = time.getValue();

                if(start.isEqual(startTime) || start.isEqual(endTime)
                        || (start.isAfter(startTime) && start.isBefore(endTime))
                        || end.isEqual(endTime) || end.isEqual(startTime)
                        || (end.isAfter(startTime) && end.isBefore(endTime))
                        || (start.isBefore(startTime) && end.isAfter(endTime))){
                    ++counter;
                }
            }
        }
        catch(Throwable throwable){throwable.printStackTrace();}

        if(counter > 0){
            isInvalid = true;
        }

        return isInvalid;
    }

    /** This method returns the name of a month from the input month number.
     * @param monthNum The month number to be referenced for a name
     * @return monthName The name of the month*/
    public static String getCldrMonthName(String monthNum){
        String monthName = "";
        switch (monthNum) {
            case "01":
                monthName = "January";
                break;
            case "02":
                monthName = "February";
                break;
            case "03":
                monthName = "March";
                break;
            case "04":
                monthName = "April";
                break;
            case "05":
                monthName = "May";
                break;
            case "06":
                monthName = "June";
                break;
            case "07":
                monthName = "July";
                break;
            case "08":
                monthName = "August";
                break;
            case "09":
                monthName = "September";
                break;
            case "10":
                monthName = "October";
                break;
            case "11":
                monthName = "November";
                break;
            case "12":
                monthName = "December";
                break;
        }
        return monthName;
    }

    /** This method returns the number of a month from the input month name.
     * @param monthName The month name to be referenced for a number
     * @return monthNum The number of the month*/
    public static String getClndMonthNum(String monthName){

        String monthNum = "";

        switch (monthName) {
            case "January":
                monthNum = "01";
                break;
            case "February":
                monthNum = "02";
                break;
            case "March":
                monthNum = "03";
                break;
            case "April":
                monthNum = "04";
                break;
            case "May":
                monthNum = "05";
                break;
            case "June":
                monthNum = "06";
                break;
            case "July":
                monthNum = "07";
                break;
            case "August":
                monthNum = "08";
                break;
            case "September":
                monthNum = "09";
                break;
            case "October":
                monthNum = "10";
                break;
            case "November":
                monthNum = "11";
                break;
            case "December":
                monthNum = "12";
                break;
            default:
                monthNum = "Invalid month name";
                break;
        }
        return monthNum;

    }

    /** This method provides a confirmation message based on the input message that the confirmation message is
     * referencing. Users can select from two buttons. One to confirm or cancel the action.
     * @param message The message to output
     * @return choice The option selected by the user*/
    public static Optional<ButtonType> getDelConfirm(String message){
        Alert alertDelConfirm  = new Alert(Alert.AlertType.CONFIRMATION,(rb.getString(message)));
        alertDelConfirm.setTitle(rb.getString("deleteConfirmTit"));
        alertDelConfirm.setHeaderText(rb.getString("deleteConfirmHead"));
        Optional<ButtonType> choice = alertDelConfirm.showAndWait();
        return choice;
    }

    /** This method provides a message for the successful deletion of an appointment from a database. It prompts users
     * with the appointment's ID and type. And requires that the user acknowledge the message by clicking on an OK
     * button to exit the message and continue with the program.
     * @param appointment The appointment deleted*/
    public static void getAptDeleteSucsMesg(Appointments appointment){
        String message =  rb.getString("deleteAptSucsMsg1") + appointment.getAppointmentId() + "\n\n" +
                rb.getString("deleteAptSucsMsg2") + appointment.getType() + "\n\n" + rb.getString("deleteAptHdMsg");

        Alert aptDeleteScsfl = new Alert(Alert.AlertType.INFORMATION);
        aptDeleteScsfl.setTitle("");
        aptDeleteScsfl.setHeaderText(rb.getString("deleteAptHdMsg"));
        aptDeleteScsfl.setContentText(message);
        aptDeleteScsfl.showAndWait();
    }

    /** This method provides a message for the successful deletion of a customer from a database. It prompts users
     * with the customer's ID and name. And requires that the user acknowledge the message by clicking on an OK
     * button to exit the message and continue with the program.
     * @param customer The customer deleted*/
    public static void getCustDeleteSucsMesg(Customers customer){
        String message =  rb.getString("deleteCustSucsMsg1") + customer.getCustomerId() + "\n\n" +
                rb.getString("deleteCustSucsMsg2") + " " + customer.getCustomerName() +
                "\n\n" + rb.getString("deleteCustHdMsg");

        Alert custDeleteScsfl = new Alert(Alert.AlertType.INFORMATION);
        custDeleteScsfl.setTitle("");
        custDeleteScsfl.setHeaderText(rb.getString("deleteCustHdMsg"));
        custDeleteScsfl.setContentText(message);
        custDeleteScsfl.showAndWait();
    }

    /** This method prompts the user with a message stating that the information entered was successfully saved
     * @param message The message to output*/
    public static void saveSuccessMessage(String message){
        Alert saveScsfl = new Alert(Alert.AlertType.INFORMATION);
        saveScsfl.setTitle("");
        saveScsfl.setHeaderText(rb.getString("savedLabel"));
        saveScsfl.setContentText(message);
        saveScsfl.showAndWait();
    }

    /** This method provides an error message stating that there was a problem connecting to the database.*/
    public static void databsConErrMsg(){
        Alert conErrMsg = new Alert(Alert.AlertType.ERROR);
        conErrMsg.setTitle("");
        conErrMsg.setHeaderText("ERROR");
        conErrMsg.setContentText(rb.getString("sqlConnErrStmt"));
        conErrMsg.showAndWait();
    }

    /** This message provides an error message stating that there was a problem loading an application page.*/
    public static void pageLoadErrMsg(){
        Alert conErrMsg = new Alert(Alert.AlertType.ERROR);
        conErrMsg.setTitle("");
        conErrMsg.setHeaderText("ERROR");
        conErrMsg.setContentText(rb.getString("pageLoadErrMsg"));
        conErrMsg.showAndWait();
    }

    /** This method provides a message to users that there appointment search query was not found, and also the
     * correct formats for entering name or date information into the appointments search text field.*/
    public static void searchInfoAptsMsg(){
        Alert srchInfoAptsMsg = new Alert(Alert.AlertType.INFORMATION);
        srchInfoAptsMsg.setHeight(500);
        srchInfoAptsMsg.setWidth(500);
        srchInfoAptsMsg.setTitle("");
        srchInfoAptsMsg.setHeaderText(rb.getString("notFoundTit"));
        srchInfoAptsMsg.setContentText(rb.getString("searchInfoApts"));
        srchInfoAptsMsg.showAndWait();
    }

    /** This method provides a message to users that there customer search query was not found, and also the
     * correct formats for entering name or phone number information into the appointments search text field.*/
    public static void searchInfoCustMesg(){
        Alert srchInfoCust = new Alert(Alert.AlertType.INFORMATION);
        srchInfoCust.setHeight(500);
        srchInfoCust.setWidth(500);
        srchInfoCust.setTitle("");
        srchInfoCust.setHeaderText(rb.getString("notFoundTit"));
        srchInfoCust.setContentText(rb.getString("searchInfo"));
        srchInfoCust.showAndWait();
    }

}
