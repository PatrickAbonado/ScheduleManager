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
import capstone.schedulemanager.dao.*;
import capstone.schedulemanager.model.*;
import capstone.schedulemanager.utilities.helpers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class controls the functionality of the Update Customer form page. Users can update a customer's customer
 * table information in the database. Users can also select a customer appointment to update, add or delete. Users can
 * sort appointments by month, week, or view all of a customer's appointments.*/
public class UpdateCustomerController implements Initializable {

    public TableView updateCustAptTable;
    public TableColumn updateCustAptAptIdCol;
    public TableColumn updateCustAptTitCol;
    public TableColumn updateCustAptDescCol;
    public TableColumn updateCustAptLocCol;
    public TableColumn updateCustAptTypeCol;
    public TableColumn updateCustAptContIdCol;
    public TableColumn updateCustAptStrtCol;
    public TableColumn updateCustAptEndCol;
    public TableColumn updateCustAptCustIdCol;
    public TableColumn updateCustAptUseIdCol;
    public TextField updateCustCustIdTex;
    public TextField updateCustNameTex;
    public TextField updateCustAddressTex;
    public TextField updateCustPstCdTex;
    public TextField updateCustPhnTex;
    public Button updateCustSvBut;
    public Button updateCustCancBut;
    public Button updateCustUpdtAptBut;
    public ComboBox updateCustStPrvComb;
    public RadioButton updateCustAllRadio;
    public ComboBox updtCustByMonComb;
    public ToggleGroup byAll;
    public Label updtCustCtryLab;
    public Label updtCustStePrvLab;
    public ComboBox updateCustCtyComb;
    public DatePicker updateCustAptByWkDtPk;
    public Label updateCustMsgLab;
    public Button updateCustAddAptBut;
    public Label updtCustTitLab;
    ObservableList<capstone.schedulemanager.model.Appointments> customerAppointments = FXCollections.observableArrayList();
    ResourceBundle rb = ResourceBundle.getBundle("Languages", Locale.getDefault());
    private static capstone.schedulemanager.model.Customers custToUpdt;

    public static capstone.schedulemanager.model.Customers getCustToUpdt() {
        return custToUpdt;
    }

    public static void setCustToUpdt(capstone.schedulemanager.model.Customers custToUpdt) {
        UpdateCustomerController.custToUpdt = custToUpdt;
    }

    /** This method initializes the Update Customer page and populates the customer's appointments table with
     * appointments data from the database. The month combo box is populated with the months that appointments
     * are scheduled. The customer data text fields are populated with the customer to update's data.
     * @param url The url provides paths for resources utilized on the Update Customer form page.
     * @param resourceBundle The resources are what is used to localize the Update Customer features and functionalities.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        updtCustTitLab.setText(custToUpdt.getCustomerName());

        String countryName = "";
        String stePrvName = "";

        updateCustMsgLab.setText("");

        for(int i = 0; i < AppointmentsData.getAptList().size(); ++i){
            if(AppointmentsData.getAptList().get(i).getCustomerId() == custToUpdt.getCustomerId()){
                customerAppointments.add(AppointmentsData.getAptList().get(i));
            }
        }

        updateCustAptTable.setItems(customerAppointments);
        updateCustAptAptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        updateCustAptTitCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        updateCustAptDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        updateCustAptLocCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        updateCustAptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        updateCustAptStrtCol.setCellValueFactory(new PropertyValueFactory<>("startDateAndTime"));
        updateCustAptEndCol.setCellValueFactory(new PropertyValueFactory<>("endDateAndTime"));
        updateCustAptContIdCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        updateCustAptUseIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        updateCustAptCustIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        updateCustCustIdTex.setText(String.valueOf(custToUpdt.getCustomerId()));
        updateCustNameTex.setText(custToUpdt.getCustomerName());
        updateCustAddressTex.setText(custToUpdt.getAddress());
        updateCustPstCdTex.setText(custToUpdt.getPostalCode());
        updateCustPhnTex.setText(custToUpdt.getPhoneNum());

        countryName = CountriesData.getCountryName(custToUpdt.getDivisionId());
        stePrvName = FirstLevelDivisionsData.getDivisionName(custToUpdt.getDivisionId());

        updateCustCtyComb.setValue(countryName);
        updateCustStPrvComb.setValue(stePrvName);

        for (Countries country: CountriesData.getCntryList()) {
            updateCustCtyComb.getItems().add(country.getCountryName());
        }

        int ctyId = CountriesData.getCountryId(countryName);
        for(FirstLevelDivisions firstLevelDivisions : FirstLevelDivisionsData.getFstLvList()){
            if(firstLevelDivisions.getCountryId() == ctyId){
                if(!updateCustStPrvComb.getItems().contains(firstLevelDivisions.getDivisionName())){
                    updateCustStPrvComb.getItems().add(firstLevelDivisions.getDivisionName());
                }
            }
        }

    }

    /** This method controls the country combo on the Update Customer form. This combo box is initially set with the
     * value of the customer being updated country. If another country is chosen, the state or province combo box is
     * populated with that country's states or provinces. */
    @FXML
    protected void onUpdateCustCtyCombClick(){

        updateCustStPrvComb.getItems().clear();

        int countryId = 0;

        for(capstone.schedulemanager.model.Countries country : CountriesData.getCntryList()){
            if(country.getCountryName().equals(String.valueOf(updateCustCtyComb.getValue()))){
                countryId = country.getCountryId();
            }
        }

        for (capstone.schedulemanager.model.FirstLevelDivisions division : FirstLevelDivisionsData.getFstLvList()){
            if(countryId == division.getCountryId()){
                updateCustStPrvComb.getItems().add(division.getDivisionName());
            }
        }
    }

    /** This method controls the radio button underneath the appointments table. This method sets the data
     * on the appointments table to all appointments currently in the system.*/
    @FXML
    protected void onUpdateCustAptAllRadioClick(){

        updateCustAptTable.setItems(customerAppointments);
    }

    /** This method controls the functionality of the update button underneath the customer's appointments table.
     * This method closes the Update Customer page and loads the Appointments page. This
     * method sets the transaction on the appointments page to the update of the selected appointment on
     * the customer's appointments table. After a selection is detected and the update button is clicked the
     * Appointments page is initialized with the appointment data from the selected appointment.
     * The action event is the clicking of the update button after the selection of an appointment from the
     * customer appointments table.
     * @param actionEvent Update button
     * */
    @FXML
    protected void onUpdateCustUpdtButClick(ActionEvent actionEvent) {

        capstone.schedulemanager.model.Appointments selectedAppointment = (capstone.schedulemanager.model.Appointments) updateCustAptTable.getSelectionModel().getSelectedItem();

        if (selectedAppointment == null){
            updateCustMsgLab.setText(rb.getString("nullSelectionDetected"));
            return;
        }

        AppointmentsController.setAptToUpdate(selectedAppointment);

        AppointmentsController.setIsAptUpdtOnly(true);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("/capstone/schedulemanager/view/Appointments.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
            stage.setScene(scene);
            stage.show();
        }
        catch(IOException ioe){
            helpers.pageLoadErrMsg();
        }
    }

    /** This method controls the Add button underneath the appointments table. After users click the Add button the
     * appointment transaction type is set to not an appointment update. The Update Customer page is then closed. After the Update
     * Customer page is closed the Appointments page is loaded and initialized with the customer to update data
     * The actionEvent is the clicking of the Add button.
     * @param actionEvent Add button*/
    @FXML
    protected void onUpdateCustAddAptBut(ActionEvent actionEvent) {

        AppointmentsController.setIsAptUpdtOnly(false);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("/capstone/schedulemanager/view/Appointments.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
            stage.setScene(scene);
            stage.show();
        }
        catch(IOException ioe){
            helpers.pageLoadErrMsg();
        }
    }

    /** This method controls the save button functionality. After input in all fields of the form is confirmed, the
     * data of the customer with the matching customer ID is updated with the field data on the customers table of the
     * mysql database. The customers table on the page is updated with the added customer table and next customer ID
     * information.*/
    @FXML
    protected void onUpdateCustSvButClick() {

        if(updateCustCustIdTex.getText().isBlank() || updateCustNameTex.getText().isBlank()
        || updateCustAddressTex.getText().isBlank() || updateCustPstCdTex.getText().isBlank()
        || updateCustPhnTex.getText().isBlank() || updateCustCtyComb.getValue() == null
        || updateCustStPrvComb.getValue() == null){
            updateCustMsgLab.setText(rb.getString("nullValueDetected"));
            return;
        }

        int custId = Integer.parseInt(updateCustCustIdTex.getText());
        String name = updateCustNameTex.getText();
        String address = updateCustAddressTex.getText();
        String postal = updateCustPstCdTex.getText();
        String phone = updateCustPhnTex.getText();

        String stePrv = String.valueOf(updateCustStPrvComb.getValue());
        int divId = FirstLevelDivisionsData.getDivisionId(stePrv);

        LocalDateTime lastUpdt = LocalDateTime.now().withNano(0);

        String lastUpdtBy = "";
        for (capstone.schedulemanager.model.Users user: UsersData.getUsrsList()) {
            if(user.getUserId() == UsersData.getUsrDatUsrId()){
                lastUpdtBy = user.getUserName();
            }
        }

        int count = CustomersData.updateCustomer(custId, name, address,
                postal, phone, lastUpdt, lastUpdtBy, divId);

        if (count == 0){
            updateCustMsgLab.setText(rb.getString("sqlConnErrStmt"));
        }
        else{
            UsersData.createUserUpdateReport();
            helpers.saveSuccessMessage(rb.getString("custSvdMessg"));
        }

        updtCustTitLab.setText(name);
    }

    /** This button controls the cancel button functionality. After a user clicks this button, the Update Customer
     * page is closed and the Menu page is loaded and initialized.
     * The action event is the pressing of the cancel button
     * @param actionEvent Cancel button*/
    @FXML
    protected void onUpdateCustCancButClick(ActionEvent actionEvent) {

        FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("/capstone/schedulemanager/view/MainMenu.fxml"));
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

    /** This method controls the functionality of the date picker underneath the customer's appointments table.
     * After a user selects a date on the calendar, the customer's appointments table is filtered to show only
     * appointments occurring from the day selected to 6 days past the selected day for a total of 7 days.*/
    @FXML
    protected void onUpdateCustAptByWkDtPkClick(){

        updtCustByMonComb.setValue(null);
        byAll.selectToggle(null);

        ObservableList<capstone.schedulemanager.model.Appointments> byWkApt = FXCollections.observableArrayList();

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate targetDate = LocalDate.parse(String.valueOf(updateCustAptByWkDtPk.getValue()), format);

        for(int i = 0; i < customerAppointments.size(); ++i){
            LocalDate date = customerAppointments.get(i).getStartDateAndTime().toLocalDate();

            if(date.equals(targetDate) || (date.isAfter(targetDate) && date.isBefore(targetDate.plusDays(7)))){
                byWkApt.add(customerAppointments.get(i));
            }
        }

        updateCustAptTable.setItems(byWkApt);
    }

    /** This method controls the functionality of the Delete button underneath the customer's appointments table.
     * After a user selects an appointment from the table and clicks the Delete button, the user is prompted
     * with a confirmation message. If the user cancels the confirmation, no data changes are made. If the user
     * confirms the deletion, the selected appointment is deleted from the database and the user is prompted with
     * a delete message that provides the user ID and type of the appointment deleted.
     * The action even is the clicking of the Delete button.
     * @param  actionEvent Delete button*/
    @FXML
    protected void onUpdateCustDeleteAptButClick(ActionEvent actionEvent) {

        capstone.schedulemanager.model.Appointments appointment = (capstone.schedulemanager.model.Appointments) updateCustAptTable.getSelectionModel().getSelectedItem();

        if(appointment == null){
            updateCustMsgLab.setText(rb.getString("nullSelectionDetected"));
            return;
        }

        Optional<ButtonType> choice = helpers.getDelConfirm("deleteConfirm");

        if(choice.isPresent() && choice.get() == ButtonType.OK){
            int check = AppointmentsData.deleteAppointment(appointment.getAppointmentId());


            if(check > 0){
                UsersData.createUserDeleteReport();
                helpers.getAptDeleteSucsMesg(appointment);
            }
            else{
                updateCustMsgLab.setText(rb.getString("sqlConnErrStmt"));
            }
        }

        FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("/capstone/schedulemanager/view/UpdateCustomer.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 965, 625);
        } catch (IOException e) {
            e.printStackTrace();
            helpers.pageLoadErrMsg();
        }
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    protected void onUpdtCustByMonRadClick(){

        ObservableList<capstone.schedulemanager.model.Appointments> aptsByMon = FXCollections.observableArrayList();
        LocalDate currDate = LocalDate.now();

        for(int i = 0; i < customerAppointments.size(); ++i){
            if(currDate.isEqual(customerAppointments.get(i).getStartDateAndTime().toLocalDate()) ||
                    (customerAppointments.get(i).getStartDateAndTime().toLocalDate().isAfter(currDate) &&
                            customerAppointments.get(i).getStartDateAndTime().toLocalDate().isBefore(currDate.plusDays(30)))){

                aptsByMon.add(customerAppointments.get(i));

            }
        }

        updateCustAptTable.setItems(aptsByMon);

    }

    @FXML
    protected void onUpdtCustByWeekRadClick(){

        ObservableList<capstone.schedulemanager.model.Appointments> aptsByMon = FXCollections.observableArrayList();
        LocalDate currDate = LocalDate.now();

        for(int i = 0; i < customerAppointments.size(); ++i){
            if(currDate.isEqual(customerAppointments.get(i).getStartDateAndTime().toLocalDate()) ||
                    (customerAppointments.get(i).getStartDateAndTime().toLocalDate().isAfter(currDate) &&
                            customerAppointments.get(i).getStartDateAndTime().toLocalDate().isBefore(currDate.plusDays(7)))){

                aptsByMon.add(customerAppointments.get(i));

            }
        }

        updateCustAptTable.setItems(aptsByMon);

    }

}