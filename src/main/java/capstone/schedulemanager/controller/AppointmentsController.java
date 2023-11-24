package capstone.schedulemanager.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import capstone.schedulemanager.Driver;
import capstone.schedulemanager.dao.AppointmentsData;
import capstone.schedulemanager.dao.ContactsData;
import capstone.schedulemanager.dao.CustomersData;
import capstone.schedulemanager.dao.UsersData;
import capstone.schedulemanager.model.*;
import capstone.schedulemanager.utilities.Element;
import capstone.schedulemanager.utilities.helpers;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/** This class controls the functionality of the Appointments form page. Users can access this form
 * initially through the update or addition of an appointment from either the main menu page or update customer page.
 * The customer and appointment ID fields are auto-populated. After the user inputs data in the all fields and
 * combo-boxes, and after the user clicks save, the data is either updated or inserted into the appointments table.
 * */
public class AppointmentsController implements Initializable {

    public TableView addAptAptTable;
    public TableColumn aptCustAptIdCol;
    public TableColumn aptCustAptTitCol;
    public TableColumn aptCustAptDescCol;
    public TableColumn aptCustAptLocCol;
    public TableColumn aptCustAptTypeCol;
    public TableColumn aptCustAptStrtCol;
    public TableColumn aptCustAptEndCol;
    public TableColumn aptCustAptCustIdCol;
    public TextField addAptAptIdText;
    public TextField addAptTitText;
    public TextField addAptDescText;
    public TextField addAptLocText;
    public TextField addAptTypText;
    public ComboBox addAptContComb;
    public ComboBox addAptStrtHrComb;
    public ComboBox addAptEndHrComb;
    public ComboBox addAptStrtMinComb;
    public ComboBox addAptEndMinComb;
    public Button addAptSvBut;
    public Button addAptCancBut;
    public Button addAptUpdtBut;
    public TableColumn aptCustAptContIdCol;
    public Label addAppCnSvMesLab;
    public DatePicker addAptDatePick;
    public DatePicker aptAllAptByWkDtPk;
    public TableView aptAllAptTable;
    public TableColumn aptAllAptIdCol;
    public TableColumn aptAllTitCol;
    public TableColumn aptAllDescCol;
    public TableColumn aptAllLocCol;
    public TableColumn aptAllTypeCol;
    public TableColumn aptAllEndCol;
    public TableColumn aptAllContNameCol;
    public TableColumn aptAllStrtCol;
    public TableColumn aptAllCustUsrIdCol;
    public TableColumn aptAllCustCustNmCol;
    public ToggleGroup aptAllAptbyAllTog;
    public ToggleGroup aptCustAptbyAllTog;
    public ComboBox aptCstIdCom;
    public ComboBox aptUsrIdCom;
    public TableColumn aptAllCustCustIdCol;
    public Label custAptTitLab;
    public RadioButton aptCustAptByAllRad;
    public ComboBox addAptByMonComb;
    public DatePicker aptCustAptByWeekDtPk;
    public Button aptAllCustAptUpdtBut;
    public RadioButton aptAllAptByAllRad;
    public ComboBox aptAllAptByMonComb;
    public Button aptCustAddtBut;
    public Button aptAllAptDelBut;
    public Button aptCustAptDelBut;
    public Button aptAllAptAddBut;
    public TableColumn aptCustAptCntNmCol;
    ResourceBundle rb = ResourceBundle.getBundle("Languages", Locale.getDefault());
    private ObservableList<capstone.schedulemanager.model.AptWithName> cstAptsWthNms = FXCollections.observableArrayList();
    private ObservableList<capstone.schedulemanager.model.AptWithName> aptsWithNameApts = AppointmentsData.getAptsWtNmsList();
    ObservableList<capstone.schedulemanager.model.Customers> customerList = CustomersData.getCustList();
    private static capstone.schedulemanager.model.Appointments aptToUpdate;
    private static boolean isAptUpdtOnly = false;
    ZoneId zid;
    int strtHr;
    int endHr;
    private LocalDateTime strtDtTm;
    private LocalDateTime endDtTm;

    /**
     * @return the isAptUpdtOnly
     */
    public static boolean isIsAptUpdtOnly() {
        return isAptUpdtOnly;
    }

    //onAptAllAptByWkDtPkClick

    /**This method is set to determine whether an appointment transaction is either
     * an update or an insert operation.
     * @param isAptUpdtOnly the isAptUpdtOnly to set
     */
    public static void setIsAptUpdtOnly(boolean isAptUpdtOnly) {
        AppointmentsController.isAptUpdtOnly = isAptUpdtOnly;
    }


    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /** Declaration of a Lambda expression used to extract the month number from a date as a String type.
     * @return the month number as a String type*/
    Element month = date -> date.split("-")[1];

    /** This method initializes the Appointments form page gui features. In this method the customer
     * and appointment tables, the combo boxes, and text fields are populated. Depending on whether
     * an appointment transaction is either an update of an existing appointment or the insertion
     * of a new appointment determines what information is populated in the form fields.
     * LAMBDA EXPRESSION 2 (getElement()): Extracts an element from a String. This was made for the reuse and
     * simplification of code.
     * @param url The url provides paths for resources utilized on the Appointments form page.
     * @param resourceBundle The resources are what is used to localize the Appointments page features
     *                       and functionalities.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        zid = ZoneId.systemDefault();

        strtHr = helpers.getZoneStrtHrConv(zid);
        endHr = helpers.getZoneEndHrConv(zid);

        if(UpdateCustomerController.getCustToUpdt() != null){

            for (AptWithName aptWithName : aptsWithNameApts) {
                if (aptWithName.getCustomerId() == UpdateCustomerController.getCustToUpdt().getCustomerId()) {
                    cstAptsWthNms.add(aptWithName);
                }
            }


            custAptTitLab.setText(UpdateCustomerController.getCustToUpdt().getCustomerName() + "'s Appointments");

        }

        aptAllAptTable.setItems(aptsWithNameApts);
        aptAllAptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        aptAllTitCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        aptAllDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        aptAllLocCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        aptAllTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        aptAllStrtCol.setCellValueFactory(new PropertyValueFactory<>("startDateAndTime"));
        aptAllEndCol.setCellValueFactory(new PropertyValueFactory<>("endDateAndTime"));
        aptAllCustUsrIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        aptAllContNameCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        aptAllCustCustNmCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        aptAllCustCustIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));


        addAptAptTable.setItems(cstAptsWthNms);
        aptCustAptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        aptCustAptTitCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        aptCustAptDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        aptCustAptLocCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        aptCustAptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        aptCustAptStrtCol.setCellValueFactory(new PropertyValueFactory<>("startDateAndTime"));
        aptCustAptEndCol.setCellValueFactory(new PropertyValueFactory<>("endDateAndTime"));
        aptCustAptCustIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        aptCustAptContIdCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        aptCustAptCntNmCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));




        for (int i = 0; i < cstAptsWthNms.size(); ++i) {
            LocalDateTime startDateAndTime = null;

            startDateAndTime = cstAptsWthNms.get(i).getStartDateAndTime();

            /** Lambda used to extract the month number from a customer's appointment start date and time.*/
            String monthNum = month.getElement(startDateAndTime.toLocalDate().toString());

            String monthName = helpers.getCldrMonthName(monthNum);

            if (!addAptByMonComb.getItems().contains(monthName)) {
                addAptByMonComb.getItems().add(monthName);
            }
        }

        for (int i = 0; i < aptsWithNameApts.size(); ++i) {
            LocalDateTime startDateAndTime = null;

            startDateAndTime = aptsWithNameApts.get(i).getStartDateAndTime();

            /** Lambda used to extract the month number from a customer's appointment start date and time.*/
            String monthNum = month.getElement(startDateAndTime.toLocalDate().toString());

            String monthName = helpers.getCldrMonthName(monthNum);

            if (!aptAllAptByMonComb.getItems().contains(monthName)) {
                aptAllAptByMonComb.getItems().add(monthName);
            }
        }

        ObservableList<capstone.schedulemanager.model.Customers> customerList = CustomersData.getCustList();
        for(int i = 0; i < customerList.size(); ++i){
            int custId = customerList.get(i).getCustomerId();
            if(!aptCstIdCom.getItems().contains(custId)){
                aptCstIdCom.getItems().add(custId);
            }
        }

        ArrayList<capstone.schedulemanager.model.Users> userList = UsersData.getUsrsList();
        for(int i = 0; i < userList.size(); ++i){
            int userId = userList.get(i).getUserId();
            if(!aptUsrIdCom.getItems().contains(userId)){
                aptUsrIdCom.getItems().add(userId);
            }
        }

        for (int i = 0; i < 60; i++) {
            String units = (i < 10) ? "0" + i : String.valueOf(i);
            addAptStrtMinComb.getItems().add(units);
            addAptEndMinComb.getItems().add(units);
        }

        if (zid.equals(ZoneId.of("Europe/London"))) {

            for (int i = strtHr; i < 24; ++i) {
                String hours = String.valueOf(i);
                addAptStrtHrComb.getItems().add(hours);
                addAptEndHrComb.getItems().add(hours);
            }
            for (int i = 0; i <= endHr; ++i) {
                String hours = "0" + i;
                addAptStrtHrComb.getItems().add(hours);
                addAptEndHrComb.getItems().add(hours);
            }
        }
        else
        {
            for (int i = strtHr; i <= endHr; i++) {
                String hours = (i < 10) ? "0" + i : String.valueOf(i);
                addAptStrtHrComb.getItems().add(hours);
                addAptEndHrComb.getItems().add(hours);
            }
        }

        for(capstone.schedulemanager.model.Contacts contact : ContactsData.getCtsList()){
            addAptContComb.getItems().add(contact.getContactName());
        }

        if(UpdateCustomerController.getCustToUpdt() != null){
            aptCstIdCom.setValue(String.valueOf(UpdateCustomerController.getCustToUpdt().getCustomerId()));
        }


        /** Lambda declaration that extracts the hour from a LocalDateTime object as a String.*/
        Element hour = timeAndDate -> timeAndDate.split("T")[1].split(":")[0];

        /** Lambda declaration that extracts the minute from a LocalDateTime object as a String.*/
        Element minute = timeAndDate -> timeAndDate.split("T")[1].split(":")[1];

        if(isAptUpdtOnly){

            String custName = "";

            for(int i = 0; i < customerList.size(); ++i){
                if(customerList.get(i).getCustomerId() == aptToUpdate.getCustomerId()){
                    custName = customerList.get(i).getCustomerName();

                }
            }

            custAptTitLab.setText(custName + "'s Appointments");

            addAptAptIdText.setText(String.valueOf(aptToUpdate.getAppointmentId()));
            addAptDatePick.setValue(aptToUpdate.getStartDateAndTime().toLocalDate());

            /** Lambda used to extract the hour number from the appointment start time as a String.*/
            String hourString = hour.getElement(aptToUpdate.getStartDateAndTime().toString());
            addAptStrtHrComb.setValue(hourString);

            /** Lambda used to extract the minute number from the appointment start time as a String.*/
            String minuteString = minute.getElement(aptToUpdate.getStartDateAndTime().toString());
            addAptStrtMinComb.setValue(minuteString);

            /** Lambda used to extract the hour number from the appointment end time as a String.*/
            hourString = hour.getElement(aptToUpdate.getEndDateAndTime().toString());
            addAptEndHrComb.setValue(hourString);

            /** Lambda used to extract the minute number from the appointment end time as a String.*/
            minuteString = minute.getElement(aptToUpdate.getEndDateAndTime().toString());
            addAptEndMinComb.setValue(minuteString);

            addAptTitText.setText(aptToUpdate.getTitle());
            addAptDescText.setText(aptToUpdate.getDescription());
            aptUsrIdCom.setValue(aptToUpdate.getUserId());
            addAptTypText.setText(aptToUpdate.getType());
            addAptLocText.setText(aptToUpdate.getLocation());
            addAptContComb.setValue(ContactsData.getContactName(aptToUpdate.getContactId()));
        }
        else{
            addAptAptIdText.setText(String.valueOf(AppointmentsData.getNextAptId()));
            addAptDatePick.setValue(null);
            addAptStrtHrComb.setValue(null);
            addAptStrtMinComb.setValue(null);
            addAptEndHrComb.setValue(null);
            addAptEndMinComb.setValue(null);
            addAptTitText.setText("");
            addAptDescText.setText("");
            aptUsrIdCom.setValue(UsersData.getUsrDatUsrId());
            addAptTypText.setText("");
            addAptLocText.setText("");
            addAptContComb.setValue(null);
        }

        addAppCnSvMesLab.setText("");
    }

    /**
     * This method sets the appointment to be updated.
     * @param appointment the appointment to set
     */
    public static void setAptToUpdate(capstone.schedulemanager.model.Appointments appointment){
        aptToUpdate = appointment;
    }

    /**
     * @return the aptToUpdate
     */
    public static capstone.schedulemanager.model.Appointments getAptToUpdate(){
        return aptToUpdate;
    }

    /** This method controls the Save button functionality on the Appointments form. After inputting valid data in all
     * fields and combo boxes on the form, the data is either updated or inserted into the appointments table.
     * The action event is the clicking of the Save button.
     * @param actionEvent Save button
     */
    @FXML
    protected void onAptSaveClick(ActionEvent actionEvent) {

        if(addAptDatePick.getValue() == null || addAptStrtHrComb.getValue() == null ||
                addAptStrtMinComb.getValue() == null || addAptEndHrComb == null ||
                addAptEndMinComb.getValue() == null || addAptTitText.getText().isBlank() ||
                addAptDescText.getText().isBlank() || addAptTypText.getText().isBlank() ||
                addAptLocText.getText().isBlank() || addAptContComb.getValue() == null ||
                addAptAptIdText.getText().isBlank() || aptCstIdCom.getValue() == null
                || aptUsrIdCom.getValue() == null){
            addAppCnSvMesLab.setText(rb.getString("nullValueDetected"));
            return;
        }

        int appointmentId = Integer.parseInt(addAptAptIdText.getText());

        int userId = Integer.parseInt(String.valueOf(aptUsrIdCom.getValue()));;

        String title = addAptTitText.getText();
        String description = addAptDescText.getText();
        int custId = Integer.parseInt(String.valueOf(aptCstIdCom.getValue()));
        String type = addAptTypText.getText();
        String location = addAptLocText.getText();

        String contName = String.valueOf(addAptContComb.getValue());
        int contactId = ContactsData.getContactId(contName);

        LocalDateTime createDate = LocalDateTime.now().withNano(0);

        String createdBy = "script";
        for(capstone.schedulemanager.model.Users user : UsersData.getUsrsList()){
            if(user.getUserId() == userId){
                createdBy = user.getUserName();
            }
        }


        LocalDateTime lastUpdt = createDate;

        String lastUpdtBy = "";
        for(capstone.schedulemanager.model.Users user : UsersData.getUsrsList()){
            if(user.getUserId() == userId){
                lastUpdtBy = user.getUserName();
            }
        }

        String apptDate = String.valueOf(addAptDatePick.getValue());
        String startHr = String.valueOf(addAptStrtHrComb.getValue());
        String startMin = String.valueOf(addAptStrtMinComb.getValue());
        String startDatAndTime = apptDate + " " + startHr + ":" + startMin + ":00";
        strtDtTm = LocalDateTime.parse(startDatAndTime, format);

        String strEndHr = String.valueOf(addAptEndHrComb.getValue());
        String strEndMin = String.valueOf(addAptEndMinComb.getValue());
        String endDatAndTime = apptDate + " " + strEndHr + ":" + strEndMin + ":00";
        endDtTm = LocalDateTime.parse(endDatAndTime, format);

        LocalDateTime now = LocalDateTime.now().withNano(0);

        if(strtDtTm.isBefore(now) || endDtTm.isBefore(now)){
            addAppCnSvMesLab.setText(rb.getString("timeTravel"));
            return;
        }

        if(strtDtTm.isEqual(endDtTm)){
            addAppCnSvMesLab.setText(rb.getString("invalidTimeEntryEqualStEnd"));
            return;
        }

        if(strtDtTm.isAfter(endDtTm)){
            addAppCnSvMesLab.setText(rb.getString("invalidTimeEntryStEnConf"));
            return;
        }

        LocalTime closeTime = LocalTime.of(endHr,0);
        LocalTime justOpenTime = strtDtTm.toLocalTime();
        LocalTime justCloseTime = endDtTm.toLocalTime();

        if(justOpenTime.equals(closeTime) || justOpenTime.isAfter(closeTime)
        || justCloseTime.isAfter(closeTime)){
            addAppCnSvMesLab.setText(rb.getString("invalidTimeEntryBusHrs"));
            return;
        }

        Map<LocalDateTime, LocalDateTime> startAndEndTimes = new HashMap<>();

        if(isAptUpdtOnly){


            for(int i = 0; i < aptsWithNameApts.size(); ++i){
                if(!(aptsWithNameApts.get(i).getAppointmentId() == aptToUpdate.getAppointmentId())){
                    LocalDateTime aptStrt = aptsWithNameApts.get(i).getStartDateAndTime();
                    LocalDateTime aptEnd = aptsWithNameApts.get(i).getEndDateAndTime();
                    startAndEndTimes.put(aptStrt, aptEnd);
                }
            }

            if(helpers.isInvalidTimeDate(startAndEndTimes,strtDtTm,endDtTm)){
                addAppCnSvMesLab.setText(rb.getString("invalidTimeEntryAptConf"));
                return;
            }

            int check = AppointmentsData.updateAppointment(title, description, location, type,
                    Timestamp.valueOf(strtDtTm), Timestamp.valueOf(endDtTm), Timestamp.valueOf(lastUpdt),
                    lastUpdtBy, userId, contactId, aptToUpdate.getAppointmentId(), custId);

            if (check > 0){

                for(int i = 0; i < aptsWithNameApts.size(); ++i){
                    if(aptToUpdate.getAppointmentId() == aptsWithNameApts.get(i).getAppointmentId()){
                        aptToUpdate = aptsWithNameApts.get(i);

                        ObservableList<capstone.schedulemanager.model.Customers> customerList = CustomersData.getCustList();
                        for (capstone.schedulemanager.model.Customers customers : customerList) {
                            if (aptToUpdate.getCustomerId() == customers.getCustomerId()) {
                                UpdateCustomerController.setCustToUpdt(customers);
                            }
                        }
                    }
                }

                helpers.saveSuccessMessage(rb.getString("apptSvdMessg"));

                FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("/capstone/schedulemanager/view/Appointments.fxml"));
                Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load(), 1000, 700);
                } catch(IOException ioe){
                    ioe.printStackTrace();
                    helpers.pageLoadErrMsg();
                }
                stage.setScene(scene);
                stage.show();
            }
            else{
                addAppCnSvMesLab.setText(rb.getString("sqlConnectionIssue"));
            }
        }

        else{
            for(int i = 0; i < aptsWithNameApts.size(); ++i){

                LocalDateTime aptStrt = aptsWithNameApts.get(i).getStartDateAndTime();
                LocalDateTime aptEnd = aptsWithNameApts.get(i).getEndDateAndTime();
                startAndEndTimes.put(aptStrt, aptEnd);
            }

            if(helpers.isInvalidTimeDate(startAndEndTimes,strtDtTm,endDtTm)){

                addAppCnSvMesLab.setText(rb.getString("invalidTimeEntryAptConf"));
                return;
            }


            int check = AppointmentsData.insertAppt(custId,title,description,location,type,
                    Timestamp.valueOf(strtDtTm), Timestamp.valueOf(endDtTm), Timestamp.valueOf(createDate), createdBy,
                    Timestamp.valueOf(lastUpdt), lastUpdtBy,userId,contactId, appointmentId);

            if (check > 0){
                for(int i = 0; i < aptsWithNameApts.size(); ++i){
                    if(appointmentId == aptsWithNameApts.get(i).getAppointmentId()){
                        aptToUpdate = aptsWithNameApts.get(i);
                        for(int j = 0; j < customerList.size(); ++j){
                            if(aptToUpdate.getCustomerId() == customerList.get(j).getCustomerId()){
                                UpdateCustomerController.setCustToUpdt(CustomersData.getCustList().get(j));
                            }
                        }
                    }
                }

                helpers.saveSuccessMessage(rb.getString("apptSvdMessg"));

                FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("/capstone/schedulemanager/view/Appointments.fxml"));
                Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load(), 1000, 700);
                } catch (IOException e) {
                    helpers.pageLoadErrMsg();
                }
                stage.setScene(scene);
                stage.show();

            }
            else{
                addAppCnSvMesLab.setText(rb.getString("sqlConnectionIssue"));
            }
        }
    }

    /** This method exits the Appointments form page and opens the Main Menu page.
     * The action event is the clicking of the cancel button.
     * @param actionEvent Cancel button
     */
    @FXML
    protected void onAddAptCancelClick(ActionEvent actionEvent) {

        FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("/capstone/schedulemanager/view/MainMenu.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1000, 700);
        } catch (IOException e) {
            helpers.pageLoadErrMsg();
        }
        stage.setScene(scene);
        stage.show();
    }

    /** This method sets the transaction on the appointments page to the update of a selected appointment on
     * the customer's appointments table. After a selection is detected and the update button is clicked the
     * Appointments form page is re-initialized with the appointment data from the selected appointment.
     * The action event is the clicking of the update button after the selection of an appointment from the
     * customer appointments table.
     * @param actionEvent Update button
     */
    @FXML
    protected void onAptCustUpdtButClick(ActionEvent actionEvent)  {

        capstone.schedulemanager.model.Appointments selectedAppointment = (capstone.schedulemanager.model.Appointments) addAptAptTable.getSelectionModel().getSelectedItem();

        if(selectedAppointment == null){
            addAppCnSvMesLab.setText(rb.getString("nullSelectionDetected"));
            return;
        }

        AppointmentsController.setAptToUpdate(selectedAppointment);

        isAptUpdtOnly = true;

        for (capstone.schedulemanager.model.Customers customer: customerList) {
            if(customer.getCustomerId() == selectedAppointment.getCustomerId()){
                UpdateCustomerController.setCustToUpdt(customer);
            }
        }

        FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("/capstone/schedulemanager/view/Appointments.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1000, 700);
        } catch (IOException e) {
            e.printStackTrace();
            helpers.pageLoadErrMsg();
        }
        stage.setScene(scene);
        stage.show();
    }

    /** This method sets the transaction on the appointments page to the update of the selected appointment on
     * the all appointments with names table. After a selection is detected and the update button is clicked the
     * Appointments form page is re-initialized with the appointment data from the selected appointment.
     * The action event is the clicking of the update button after the selection of an appointment from the
     * customer appointments table.
     * @param actionEvent Update button*/
    @FXML
    protected void onAptAllCustAptUpdtBut(ActionEvent actionEvent) {

        capstone.schedulemanager.model.AptWithName selectedAppointment = (capstone.schedulemanager.model.AptWithName) aptAllAptTable.getSelectionModel().getSelectedItem();

        if(selectedAppointment == null){
            addAppCnSvMesLab.setText(rb.getString("nullSelectionDetected"));
            return;
        }

        for(int i = 0; i < AppointmentsData.getAptList().size(); ++i){
            if(AppointmentsData.getAptList().get(i).getAppointmentId() == selectedAppointment.getAppointmentId()){
                AppointmentsController.setAptToUpdate(AppointmentsData.getAptList().get(i));
            }
        }

        isAptUpdtOnly = true;

        for (capstone.schedulemanager.model.Customers customer: CustomersData.getCustList()) {

            if(customer.getCustomerId() == selectedAppointment.getCustomerId()){
                UpdateCustomerController.setCustToUpdt(customer);
            }
        }

        FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("/capstone/schedulemanager/view/Appointments.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1000, 700);
        } catch (IOException e) {
            e.printStackTrace();
            helpers.pageLoadErrMsg();
        }
        stage.setScene(scene);
        stage.show();
    }

    /** This method controls the functionality of the radio button labeled all underneath the all appointments table. This method
     * sets the data on the all appointments table to all appointments currently in the system.*/
    @FXML
    protected void onAptAllAptByAllRadClick(){

        aptAllAptTable.setItems(aptsWithNameApts);

    }


    /** This method controls the radio button labeled all underneath the customer appointments table. This method sets
     * the data on the customer's appointments table to all the customer's appointments currently in the system.*/
    @FXML
    protected void onAptCustAptByAllRadClick(){

        addAptAptTable.setItems(cstAptsWthNms);
    }

    /** This method controls the functionality of the Add appointment button underneath the customer's
     * appointments table. After a user clicks the Add button the appointments page is re-initialized
     * clearing all text fields and populating the form with data pertaining to the customer that the
     * appointment is being added for.
     * The action event is the clicking of the Add button.
     * @param actionEvent Add button
     */
    @FXML
    protected void onAptCustAddAptButClick(ActionEvent actionEvent)  {

        if(aptCstIdCom.getValue() != null){

            for(int i = 0; i < customerList.size(); ++i){
                if(customerList.get(i).getCustomerId() == Integer.parseInt(aptCstIdCom.getValue().toString())){
                    UpdateCustomerController.setCustToUpdt(customerList.get(i));
                }
            }
        }

        AppointmentsController.setIsAptUpdtOnly(false);

        FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("/capstone/schedulemanager/view/Appointments.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1000, 700);
        } catch (IOException e) {
            helpers.pageLoadErrMsg();
        }
        stage.setScene(scene);
        stage.show();
    }

    /** This method controls the functionality of the Delete button underneath the all appointments table.
     * After a user selects an appointment from the table and clicks the Delete button, the user is prompted
     * with a confirmation message. If the user cancels the confirmation, no data changes are made. If the user
     * confirms the deletion, the selected appointment is deleted from the database and the user is prompted with
     * a delete message that provides the user ID and type of the appointment deleted.
     * The action event is the clicking of the Delete button.
     * @param  actionEvent Delete button
     */
    @FXML
    protected  void onAptAllAptDelButClick(ActionEvent actionEvent) {

        capstone.schedulemanager.model.Appointments aptToDelete = null;
        int check = 0;
        capstone.schedulemanager.model.AptWithName aptWtNm = (capstone.schedulemanager.model.AptWithName) aptAllAptTable.getSelectionModel().getSelectedItem();

        if(aptWtNm == null){
            addAppCnSvMesLab.setText(rb.getString("nullSelectionDetected"));
            return;
        }

        for (AptWithName aptsWithNameApt : aptsWithNameApts) {
            if (aptsWithNameApt.getAppointmentId() == aptWtNm.getAppointmentId()) {
                aptToDelete = aptsWithNameApt;
            }
        }

        Optional<ButtonType> choice = helpers.getDelConfirm("deleteConfirm");

        if(choice.isPresent() && choice.get() == ButtonType.OK){

            check = AppointmentsData.deleteAppointment(aptToDelete.getAppointmentId());


            if(check > 0){

                helpers.getAptDeleteSucsMesg(aptToDelete);

                if(AppointmentsController.getAptToUpdate().getAppointmentId() == aptToDelete.getAppointmentId()){
                    setIsAptUpdtOnly(false);
                }

                FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("/capstone/schedulemanager/view/Appointments.fxml"));
                Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load(), 1000, 700);
                } catch (IOException e) {
                    helpers.pageLoadErrMsg();
                }
                stage.setScene(scene);
                stage.show();
            }
            else{
                addAppCnSvMesLab.setText(rb.getString("sqlConnErrStmt"));
            }


        }




    }

    /** This method controls the functionality of the Delete button underneath the customer's appointments table.
     * After a user selects an appointment from the table and clicks the Delete button, the user is prompted
     * with a confirmation message. If the user cancels the confirmation, no data changes are made. If the user
     * confirms the deletion, the selected appointment is deleted from the database and the user is prompted with
     * a delete message that provides the user ID and type of the appointment deleted.
     * The action even is the clicking of the Delete button.
     * @param  actionEvent Delete button
     */
    @FXML
    protected void onAptCustAptDelButClick(ActionEvent actionEvent) {

        capstone.schedulemanager.model.Appointments appointment = (capstone.schedulemanager.model.Appointments) addAptAptTable.getSelectionModel().getSelectedItem();

        if(appointment == null){
            addAppCnSvMesLab.setText(rb.getString("nullSelectionDetected"));
            return;
        }

        Optional<ButtonType> choice = helpers.getDelConfirm("deleteConfirm");

        if(choice.isPresent() && choice.get() == ButtonType.OK){

            if(appointment.getAppointmentId() == AppointmentsController.getAptToUpdate().getAppointmentId()){
                setIsAptUpdtOnly(false);
            }

            int check = AppointmentsData.deleteAppointment(appointment.getAppointmentId());

            if(check > 0){
                helpers.getAptDeleteSucsMesg(appointment);
            }
            else{
                addAppCnSvMesLab.setText(rb.getString("sqlConnErrStmt"));
            }
        }

        FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("/capstone/schedulemanager/view/Appointments.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1000, 700);
        } catch (IOException e) {
            helpers.pageLoadErrMsg();
        }
        stage.setScene(scene);
        stage.show();
    }

    /** This method controls the Add button underneath the all appointments table. After a user clicks this button
     * the appointments page is re-initialized. All fields are cleared except for the user ID and appointment ID.
     * The user ID combo box is populated with the current user. The appointment ID text field is populated with the
     * next appointment ID.
     * The actionEvent is the clicking of the Add button.
     * @param actionEvent Add button
     */
    @FXML
    protected void onAptAllAptAddButClick(ActionEvent actionEvent) {

        AppointmentsController.setIsAptUpdtOnly(false);
        UpdateCustomerController.setCustToUpdt(null);
        cstAptsWthNms.clear();

        FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("/capstone/schedulemanager/view/Appointments.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1000, 700);
        } catch (IOException e) {
            helpers.pageLoadErrMsg();
        }
        stage.setScene(scene);
        stage.show();
    }


    /** This method controls the functionality of the customer ID combo box on the form. When a user selects a
     * customer ID the title above the customer appointments table changes to that customer's name and the
     * customer appointments table underneath the label is populated with all of that customer's appointments.*/
    @FXML
    protected void onAptCstIdComClick(){

        String name = "";

        aptCustAptByAllRad.setSelected(true);

        cstAptsWthNms.clear();

        addAptByMonComb.getItems().clear();

        int custId = (int) aptCstIdCom.getValue();

        for(int i = 0; i < customerList.size(); ++i){
            if(custId == customerList.get(i).getCustomerId()){
                name = customerList.get(i).getCustomerName();
            }
        }

        for(int i = 0; i < aptsWithNameApts.size(); ++i){
            if(custId == aptsWithNameApts.get(i).getCustomerId()){
                cstAptsWthNms.add(aptsWithNameApts.get(i));
            }
        }

        for (int i = 0; i < cstAptsWthNms.size(); ++i){
            /** Lambda used to extract the month number from a customer's appointment start date and time.*/
            String monthNum = month.getElement(cstAptsWthNms.get(i).getStartDateAndTime().toLocalDate().toString());

            String monthName = helpers.getCldrMonthName(monthNum);

            if (!addAptByMonComb.getItems().contains(monthName)) {
                addAptByMonComb.getItems().add(monthName);
            }
        }


        custAptTitLab.setText(name + "'s Appointments");
        addAptAptTable.setItems(cstAptsWthNms);
    }

    /** This method controls the functionality of the date picker underneath the customer's appointments table.
     * After a user selects a date on the calendar, the customer's appointments table is filtered to show only
     * appointments occurring from the day selected to 6 days past the selected day for a total of 7 days.*/
    @FXML
    protected void onAptCustAptByWeekDtPkClick(){

        aptCustAptbyAllTog.selectToggle(null);

        addAptByMonComb.setValue(null);

        ObservableList<Appointments> byWkApt = FXCollections.observableArrayList();

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate targetDate = LocalDate.parse(String.valueOf(aptCustAptByWeekDtPk.getValue()), format);

        for(int i = 0; i < cstAptsWthNms.size(); ++i){
            LocalDate date = cstAptsWthNms.get(i).getStartDateAndTime().toLocalDate();

            if(date.equals(targetDate) || (date.isAfter(targetDate) && date.isBefore(targetDate.plusDays(7)))){
                byWkApt.add(cstAptsWthNms.get(i));
            }
        }

        addAptAptTable.setItems(byWkApt);
    }


    /** This method controls the functionality of the date picker located underneath the all appointments table on the
     * appointments page. When users select a date the appointments table is loaded with all scheduled appointments
     * made from the date selection to 7 days after.*/
    @FXML
    protected void onAptAllAptByWkDtPkClick(){

        aptAllAptbyAllTog.selectToggle(null);

        aptAllAptByMonComb.setValue(null);

        ObservableList<AptWithName> byWkApt = FXCollections.observableArrayList();

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate targetDate = LocalDate.parse(String.valueOf(aptAllAptByWkDtPk.getValue()), format);

        for(int i = 0; i < aptsWithNameApts.size();++i){

            LocalDate localDate = aptsWithNameApts.get(i).getStartDateAndTime().toLocalDate();

            if(localDate.equals(targetDate) || (localDate.isAfter(targetDate) && localDate.isBefore(targetDate.plusDays(7)))){
                byWkApt.add(aptsWithNameApts.get(i));
            }
        }

        aptAllAptTable.setItems(byWkApt);
    }

    /**This method controls the combo button underneath the appointments table on the appointments page. The combo
     * button is loaded at page initialization with the months names of all appointments on the database. When a user
     * selects a month the appointments table is loaded with all appointments in that month.*/
    @FXML
    protected void onAddAptByMonCombClick(){

        aptCustAptbyAllTog.selectToggle(null);

        ObservableList<capstone.schedulemanager.model.Appointments> aptByMonthList = FXCollections.observableArrayList();

        String monthNum = helpers.getClndMonthNum(String.valueOf(addAptByMonComb.getValue()));

        for(int i = 0; i < cstAptsWthNms.size(); ++i){

            /** Lambda used to extract the month number from a customer's appointment start date.*/
            if(month.getElement(cstAptsWthNms.get(i).getStartDateAndTime().toLocalDate().toString()).equals(monthNum)){
                aptByMonthList.add(cstAptsWthNms.get(i));
            }
        }

        addAptAptTable.setItems(aptByMonthList);
    }

    /** This method controls the functionality of the month combo box underneath the all appointments table.
     * After the user selects a month from the box, the appointments on the all appointments table are filtered
     * to only show those appointments occurring during that month period.*/
    @FXML
    protected void onAptAllAptByMonCombClick() {

        aptAllAptbyAllTog.selectToggle(null);

        ObservableList<capstone.schedulemanager.model.AptWithName> aptByMonthList = FXCollections.observableArrayList();

        String monthNum = helpers.getClndMonthNum(String.valueOf(aptAllAptByMonComb.getValue()));


        for(int i = 0; i < aptsWithNameApts.size(); ++i){

            /** Lambda used to extract the month number from an appointment start date.*/
            if(month.getElement(String.valueOf(aptsWithNameApts.get(i).getStartDateAndTime().toLocalDate())).equals(monthNum)){
                aptByMonthList.add(aptsWithNameApts.get(i));
            }
        }

        aptAllAptTable.setItems(aptByMonthList);

    }

}
