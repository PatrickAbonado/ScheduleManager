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
import capstone.schedulemanager.dao.CustomersData;
import capstone.schedulemanager.model.Appointments;
import capstone.schedulemanager.utilities.helpers;
import java.lang.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class controls the functionality of the main menu page. On this page users can add customers, view reports,
 * update, and delete both appointments and customers. Users first access this page after successful login validation.
 * Users are also routed back to this page from other pages after clicking the cancel or menu button from the other
 * pages. Users can sort the appointments table by month, week, or all. Users also view a message stating whether
 * there are any upcoming interviews. If there are any interviews within 15 minutes of the Main Menu initialization
 * a message prompts the user with the appointment ID, the time, and the date.*/
public class MainMenuController implements Initializable {

    public TableColumn menuCustIDCol;
    public TableColumn menuCustNameCol;
    public TableColumn menuCustAdrsCol;
    public TableColumn menuCustPhoneCol;
    public TableColumn menuCustPostCol;
    public TableColumn menuAptAptIdCol;
    public TableColumn menuAptTitCol;
    public TableColumn menuAptDescCol;
    public TableColumn menuAptLocCol;
    public TableColumn menuAptTypeCol;
    public TableColumn menuAptCustIdCol;
    public TableColumn menuAptUseIdCol;
    public Label menuMintAlertLab;
    public Button menuAptExitBut;
    public Button menuAptRepBut;
    public Button menuCustAddBut;
    public Button menuCustUpdtBut;
    public Button menuAptUpdtBut;
    public Button menuAptDelBut;
    public Button menuCustDelBut;
    public TableView menuCustomerTable;
    public TableView menuAptTable;
    public TableColumn menuAptStrtCol;
    public TableColumn menuAptEndCol;
    public TableColumn menuCustDivCol;
    public TableColumn menuAptContIdCol;
    public RadioButton menuCustApptByAllRad;
    public ToggleGroup byAll;
    public RadioButton menuAptByMonRad;
    public RadioButton menuAptByWeekRad;
    public Button menuAptAddBut;
    public TableColumn menuCustDivNmeCol;
    public TableColumn menuCustCtryNmeCol;
    public TableColumn menuAptCustNmCol;
    public Button mainAptsSrchBut;
    public TextField mainAptsSrchTxt;
    public Button mainCustSrchBut;
    public TextField mainCustSrchTxt;
    public RadioButton mainCustAllRadio;
    public ToggleGroup allCustRadTog;
    ObservableList<capstone.schedulemanager.model.AptWithName> appointments = FXCollections.observableArrayList();
    ObservableList<capstone.schedulemanager.model.CustsWthNms> customers = FXCollections.observableArrayList();
    ResourceBundle rb = ResourceBundle.getBundle("Languages", Locale.getDefault());

    /**
     * This method initializes the Menu page and populates the customers and appointments tables with data from
     * the database. The month combo box is populated with the months that appointments are scheduled.
     *
     * @param url            The url provides paths for resources utilized on the Menu form page.
     * @param resourceBundle The resources are what is used to localize the Menu features and functionalities.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        customers = CustomersData.getCustWithNmLst();
        menuCustomerTable.setItems(customers);
        menuCustIDCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        menuCustNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        menuCustAdrsCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        menuCustPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        menuCustPostCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        menuCustDivCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
        menuCustDivNmeCol.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
        menuCustCtryNmeCol.setCellValueFactory(new PropertyValueFactory<>("countryName"));

        appointments = AppointmentsData.getAptsWtNmsList();
        menuAptTable.setItems(appointments);
        menuAptAptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        menuAptCustNmCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        menuAptTitCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        menuAptDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        menuAptLocCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        menuAptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        menuAptStrtCol.setCellValueFactory(new PropertyValueFactory<>("startDateAndTime"));
        menuAptEndCol.setCellValueFactory(new PropertyValueFactory<>("endDateAndTime"));
        menuAptContIdCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        menuAptCustIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        menuAptUseIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        menuMintAlertLab.setText(rb.getString("noFifteenApt"));

        isAptInFifteen();


    }

    /**
     * This method controls the Add button functionality underneath the customers table. After users click this button
     * the Maine Menu page is closed and the Add Customer page is opened and initialized.
     * The actionEvent is the clicking of the Add button.
     *
     * @param actionEvent Add Button
     */
    @FXML
    protected void onMenuCustAddButClick(ActionEvent actionEvent) {

        FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("/capstone/schedulemanager/view/AddCustomer.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 860, 580);
        } catch (IOException e) {
            e.printStackTrace();
            helpers.pageLoadErrMsg();
        }
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method controls the update button functionality underneath the customer's table. After a user selects a
     * row from the customers table and clicks the update button, the main menu page is closed and the update customer
     * page is opened and populated with the selected customer's data.
     * The action event is the clicking of the update button
     *
     * @param actionEvent Update button
     */
    @FXML
    protected void onMenuCustUpdtButClick(ActionEvent actionEvent) {

        capstone.schedulemanager.model.Customers selectedCustomer = (capstone.schedulemanager.model.Customers) menuCustomerTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null) {
            menuMintAlertLab.setText(rb.getString("nullSelectionDetected"));
            return;
        }

        UpdateCustomerController.setCustToUpdt(selectedCustomer);

        FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("/capstone/schedulemanager/view/UpdateCustomer.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
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

    /**
     * This method controls the Exit button functionality on the Main Menu page. After a user clicks this button, the
     * Main Menu page is closed and the user is exited from the program.
     */
    @FXML
    protected void onMenuExitButClick() {
        System.exit(0);
    }

    /**
     * This method controls the functionality of the Update button underneath the appointments table. This method
     * sets the transaction on the appointments page to the update of a selected appointment on. After a selection
     * is detected and the update button is clicked the Appointments form page is initialized with the appointment data
     * from the selected appointment.
     * The action event is the clicking of the update button after the selection of an appointment from the
     * appointments table.
     *
     * @param actionEvent Update button
     */
    @FXML
    protected void onMenuAptUpdtButClick(ActionEvent actionEvent) {

        capstone.schedulemanager.model.Appointments selectedAppointment = (capstone.schedulemanager.model.Appointments) menuAptTable.getSelectionModel().getSelectedItem();

        if (selectedAppointment == null) {
            menuMintAlertLab.setText(rb.getString("nullSelectionDetected"));
            return;
        }

        AppointmentsController.setAptToUpdate(selectedAppointment);
        AppointmentsController.setIsAptUpdtOnly(true);
        for (capstone.schedulemanager.model.Customers customer : CustomersData.getCustList()) {
            if (customer.getCustomerId() == selectedAppointment.getCustomerId()) {
                UpdateCustomerController.setCustToUpdt(customer);
            }
        }

        FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("/capstone/schedulemanager/view/Appointments.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
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

    /**
     * This method controls the radio button underneath the customer appointments table. This method sets the data
     * on the appointments table to all appointments currently in the system.
     */
    @FXML
    protected void onMenuCustApptByAllRadClick() {

        mainAptsSrchTxt.clear();

        menuAptTable.setItems(appointments);
    }

    /**
     * This method controls the functionality of the Delete button underneath the appointments table.
     * After a user selects an appointment from the table and clicks the Delete button, the user is prompted
     * with a confirmation message. If the user cancels the confirmation, no data changes are made. If the user
     * confirms the deletion, the selected appointment is deleted from the database and the user is prompted with
     * a delete message that provides the user ID and type of the appointment deleted.
     * The action event is the clicking of the Delete button.
     *
     * @param actionEvent Delete button
     */
    @FXML
    protected void onMenuAptDelButClick(ActionEvent actionEvent) {

        capstone.schedulemanager.model.Appointments appointment = (capstone.schedulemanager.model.Appointments) menuAptTable.getSelectionModel().getSelectedItem();

        if (appointment == null) {
            menuMintAlertLab.setText(rb.getString("nullSelectionDetected"));
            return;
        }

        Optional<ButtonType> choice = helpers.getDelConfirm("deleteConfirm");

        if (choice.isPresent() && choice.get() == ButtonType.OK) {
            int check = AppointmentsData.deleteAppointment(appointment.getAppointmentId());


            if (check > 0) {
                helpers.createUserDeleteReport();
                helpers.getAptDeleteSucsMesg(appointment);
            } else {
                menuMintAlertLab.setText(rb.getString("sqlConnErrStmt"));
            }
        }


        FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("/capstone/schedulemanager/view/MainMenu.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
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

    /**
     * This method controls the Delete button functionality underneath the customers' table. After selecting a
     * customer from the Customers table and clicking the delete button, the customer is prompted with a confirmation
     * message. If not confirmed, no changes are made to the customers table on the database. If confirmed and the
     * customer has no scheduled appointments, the selected customer is deleted from the database. If a customer to be
     * deleted has scheduled appointments a confirmation message is sent where if confirmed the appointments are
     * deleted from the database. Each appointment deleted prompts the customer with an information alert with the
     * appointment ID and type. After the appointments are deleted, the selected customer is deleted from the customers
     * table on the database. After the customer is deleted, the user is prompted with an information message indicating
     * the customer's ID and their name. After the customer deletion process is completed the customers table and
     * customer ID text field are updated with the updated database customer table data.
     * The action event is the clicking of the delete button.
     *
     * @param actionEvent Delete button
     */
    @FXML
    protected void onMenuCustDelButClick(ActionEvent actionEvent) {
        int appsCounter = 0;
        int custCounter = 0;

        capstone.schedulemanager.model.Customers selectedCustomer = (capstone.schedulemanager.model.Customers) menuCustomerTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null) {
            menuMintAlertLab.setText(rb.getString("nullSelectionDetected"));
            return;
        }

        for (int i = 0; i < appointments.size(); ++i) {
            if (appointments.get(i).getCustomerId() == selectedCustomer.getCustomerId()) {
                appsCounter++;
            }
        }

        if (appsCounter > 0) {

            Optional<ButtonType> choice = helpers.getDelConfirm("deleteCustWtAptConfirm");

            int aptCheckCntr = 0;

            if (choice.isPresent() && choice.get() == ButtonType.OK) {

                ArrayList<capstone.schedulemanager.model.Appointments> delAptList = new ArrayList<>(AppointmentsData.getAptList());

                for (int i = 0; i < delAptList.size(); ++i) {

                    Appointments aptToDel = delAptList.get(i);

                    if (delAptList.get(i).getCustomerId() == selectedCustomer.getCustomerId()) {
                        aptCheckCntr = AppointmentsData.deleteAppointment(delAptList.get(i).getAppointmentId());
                    }
                    if (aptCheckCntr > 0) {
                        helpers.createUserDeleteReport();
                        helpers.getAptDeleteSucsMesg(aptToDel);
                    }
                }

                custCounter = CustomersData.deleteCustomer(selectedCustomer.getCustomerId());

            }
        } else {
            Optional<ButtonType> choice = helpers.getDelConfirm("deleteCust");
            if (choice.isPresent() && choice.get() == ButtonType.OK) {
                custCounter = CustomersData.deleteCustomer(selectedCustomer.getCustomerId());
            }
        }

        if (custCounter > 0) {
            helpers.createUserDeleteReport();
            helpers.getCustDeleteSucsMesg(selectedCustomer);
        } else {
            menuMintAlertLab.setText(rb.getString("sqlConnErrStmt"));
        }

        FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("/capstone/schedulemanager/view/MainMenu.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
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

    //onMenuCustApptByAllRadClick

    /**
     * This method controls the Report button functionality on the Menu page. After a user clicks this button the
     * Menu page is closed and the Reports page is loaded.
     * The actionEvent is the clicking of the Report button.
     *
     * @param actionEvent Report button
     */
    @FXML
    protected void onMenuReptButClick(ActionEvent actionEvent) {

        FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("/capstone/schedulemanager/view/Reports.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 680, 525);
        } catch (IOException e) {
            e.printStackTrace();
            helpers.pageLoadErrMsg();
        }
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method is used to check whether there is an appointment scheduled within 15 minutes of the Menu page
     * intialization. If there is no appointment scheduled a label on the lower left of the page is set with a statement
     * stating there are no upcomming appointments. If an appointment is scheduled the label is set with a statement
     * stating the minutes till the appointment, the ID, the time, and the date of the appointment.
     */
    public void isAptInFifteen() {

        LocalDateTime now = LocalDateTime.now().withNano(0);

        for (int i = 0; i < appointments.size(); ++i) {

            LocalDateTime aptStartTime = appointments.get(i).getStartDateAndTime();

            if (aptStartTime.isBefore(now.plusMinutes(60)) && aptStartTime.isAfter(now)) {
                if (now.getMinute() > aptStartTime.getMinute()) {
                    menuMintAlertLab.setText(rb.getString("fifteenAptPresent1")
                            + (appointments.get(i).getStartDateAndTime().getMinute() - now.getMinute() + 60)
                            + " " + rb.getString("fifteenAptPresent2")
                            + "\n" + rb.getString("fifteenAptPresent3") + appointments.get(i).getAppointmentId()
                            + "\t" + rb.getString("fifteenAptPresent4") + appointments.get(i).getStartDateAndTime());
                } else {
                    menuMintAlertLab.setText(rb.getString("fifteenAptPresent1")
                            + (appointments.get(i).getStartDateAndTime().getMinute() - now.getMinute())
                            + " " + rb.getString("fifteenAptPresent2")
                            + "\n" + rb.getString("fifteenAptPresent3") + appointments.get(i).getAppointmentId()
                            + "\t" + rb.getString("fifteenAptPresent4") + appointments.get(i).getStartDateAndTime());
                }
            }
        }

    }

    /**
     * This method controls the Add button underneath the all appointments table. After a user clicks this button
     * the menu page is closed and the appointments page is initialized. The user ID combo box is populated with the
     * current user. The appointment ID text field is populated with the next appointment ID.
     * The actionEvent is the clicking of the Add button.
     *
     * @param actionEvent Add button
     */
    @FXML
    protected void onMenuAptAddButClick(ActionEvent actionEvent) {

        AppointmentsController.setIsAptUpdtOnly(false);
        UpdateCustomerController.setCustToUpdt(null);

        FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("/capstone/schedulemanager/view/Appointments.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
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

    /**
     * This method controls the functionality of the month radio button underneath the appointments table.
     * When a user clicks this button a list of all appointments from the current date to 30 days after is output onto
     * the appointments table.
     */
    @FXML
    protected void onMenuAptByMonRadClick() {

        mainAptsSrchTxt.clear();

        ObservableList<capstone.schedulemanager.model.Appointments> aptsByMon = FXCollections.observableArrayList();
        LocalDate currDate = LocalDate.now();

        for (int i = 0; i < appointments.size(); ++i) {
            if (currDate.isEqual(appointments.get(i).getStartDateAndTime().toLocalDate()) ||
                    (appointments.get(i).getStartDateAndTime().toLocalDate().isAfter(currDate) &&
                            appointments.get(i).getStartDateAndTime().toLocalDate().isBefore(currDate.plusDays(30)))) {

                aptsByMon.add(appointments.get(i));

            }
        }

        menuAptTable.setItems(aptsByMon);
    }

    /**
     * This method controls the functionality of the Week radio button underneath the appointments table.
     * After a user clicks the button the list of the appointments is filtered into a new list by those dates that
     * are either equal to the current date or after the current date and before 7 days from the current date. This
     * list is then output onto the appointments table.
     */
    @FXML
    protected void onMenuAptByWeekRadClick() {

        mainAptsSrchTxt.clear();

        ObservableList<capstone.schedulemanager.model.Appointments> aptsByWeek = FXCollections.observableArrayList();
        LocalDate currDate = LocalDate.now();

        for (int i = 0; i < appointments.size(); ++i) {
            if (currDate.isEqual(appointments.get(i).getStartDateAndTime().toLocalDate()) ||
                    (appointments.get(i).getStartDateAndTime().toLocalDate().isAfter(currDate) &&
                            appointments.get(i).getStartDateAndTime().toLocalDate().isBefore(currDate.plusDays(7)))) {

                aptsByWeek.add(appointments.get(i));
            }
        }

        menuAptTable.setItems(aptsByWeek);
    }

    @FXML
    protected void onMainAptsSrchButClick() {

        boolean found = false;

        if (mainAptsSrchTxt.getText().isEmpty()) {
            helpers.searchInfoAptsMsg();
            return;
        }

        byAll.selectToggle(null);


        String input = mainAptsSrchTxt.getText().toLowerCase();


        ObservableList<capstone.schedulemanager.model.AptWithName> resultList = FXCollections.observableArrayList();
        ObservableList<capstone.schedulemanager.model.AptWithName> searchAppointments = AppointmentsData.getAptsWtNmsList();

        for (int i = 0; i < searchAppointments.size(); ++i) {

            if (searchAppointments.get(i).getCustomerName().toLowerCase().contains(input)
                    || String.valueOf(searchAppointments.get(i).getStartDateAndTime().toLocalDate()).contains(input)
                    || String.valueOf(searchAppointments.get(i).getEndDateAndTime().toLocalDate()).contains(input)) {
                resultList.add(searchAppointments.get(i));
                found = true;

            }
        }


        if(!found){
            helpers.searchInfoAptsMsg();
            return;
        }



        menuAptTable.setItems(resultList);
    }

    @FXML
    protected void onMainCustSrchButClick() {

        boolean found = false;

        if (mainCustSrchTxt.getText().isEmpty()) {
            helpers.searchInfoCustMesg();
            return;
        }

        allCustRadTog.selectToggle(null);


        String input = mainCustSrchTxt.getText().toLowerCase();


        ObservableList<capstone.schedulemanager.model.CustsWthNms> resultList = FXCollections.observableArrayList();
        ObservableList<capstone.schedulemanager.model.CustsWthNms> searchCustomers = CustomersData.getCustWithNmLst();

        for (int i = 0; i < searchCustomers.size(); ++i) {

            if (searchCustomers.get(i).getCustomerName().toLowerCase().contains(input)
                    || searchCustomers.get(i).getPhoneNum().contains(input)) {
                resultList.add(searchCustomers.get(i));
                found = true;
            }
        }

        if(!found){
            helpers.searchInfoCustMesg();
            return;
        }

        menuCustomerTable.setItems(resultList);
    }

    @FXML
    protected void mainCustAllRadioClick(){

        mainCustSrchTxt.clear();

        menuCustomerTable.setItems(customers);
    }



}


