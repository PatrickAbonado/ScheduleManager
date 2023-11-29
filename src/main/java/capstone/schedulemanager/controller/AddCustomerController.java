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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;



/** This class controls the functionality of the Add Customer form page. Customers input
 * information through text fields and combo box selection. The page also contains a table
 * with a list of the customers currently in the system for reference. Users can select a
 * customer on the list tp update or delete.
 * */
public class AddCustomerController implements Initializable  {
    public Button addCustUpdtCust;
    public TableView addCustomerTable;
    public TableColumn addCustIDCol;
    public TableColumn addCustNameCol;
    public TableColumn addCustAdrsCol;
    public TableColumn addCustPhoneCol;
    public TableColumn addCustPostCol;
    public TextField addCustNameTex;
    public TextField addCustAddressTex;
    public TextField addCustPstCdTex;
    public TextField addCustPhnTex;
    public Button addCustSvBut;
    public Button addCustCancBut;
    public ComboBox addCustStPrvComb;
    public TableColumn addCustDivCol;
    public Label addCustCtyLab;
    public ComboBox addCustCtyComb;
    public Label addCustSvMsgLab;
    public TextField addCustCustIdTex;
    ResourceBundle rb = ResourceBundle.getBundle("Languages", Locale.getDefault());
    ObservableList<Customers> customers = FXCollections.observableArrayList();
    private ArrayList<capstone.schedulemanager.model.FirstLevelDivisions> divList = new ArrayList<>();
    private ArrayList<capstone.schedulemanager.model.Countries> ctyList = new ArrayList<>();

    /** This method initializes the Add Customer page and populates the customers table. The customer ID field is
     * pre-populated with the next appointment ID. The country combo box is populated with the countries available
     * for scheduling services.
     * @param url The url provides paths for resources utilized on the Add Customer form page.
     * @param resourceBundle The resources are what is used to localize the Add Customer features and functionalities.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        divList = FirstLevelDivisionsData.getFstLvList();
        ctyList = CountriesData.getCntryList();
        customers = CustomersData.getCustList();

        addCustSvMsgLab.setText("");

        addCustomerTable.setItems(customers);
        addCustIDCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        addCustNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addCustAdrsCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        addCustPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        addCustPostCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        addCustDivCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));

        for(int i = 0; i < ctyList.size(); ++i){
            addCustCtyComb.getItems().add(ctyList.get(i).getCountryName());
        }

        addCustCustIdTex.setText(String.valueOf(CustomersData.getNextCustId()));
    }

    /** This method controls the country combo button. Users select a country from the options
     * which populates the State/Prvce combo button with options located in the selected country.*/
    @FXML
    protected void onAddCustCtyCombClick(){

        int countryId = 0;

        addCustStPrvComb.getItems().clear();

        for(capstone.schedulemanager.model.Countries country : ctyList){
            if(country.getCountryName().equals(String.valueOf(addCustCtyComb.getValue()))){
                countryId = country.getCountryId();
            }
        }

        for (capstone.schedulemanager.model.FirstLevelDivisions division : divList){
            if(countryId == division.getCountryId()){
                addCustStPrvComb.getItems().add(division.getDivisionName());
            }
        }
    }

    /** This method controls the save button functionality. After users have added input to all fields
     * of the form, the data is added to the customers table on the mysql database. The customers table
     * on the page is updated with the added customer table and next customer ID information.
     * The actionEvent is the clicking of the save button
     * @param actionEvent Save button*/
    @FXML
    protected void onAddCustSvButClick(ActionEvent actionEvent) {

        if(addCustNameTex.getText().isBlank() || addCustPhnTex.getText().isBlank() ||
        addCustAddressTex.getText().isBlank() || addCustCtyComb.getValue() == null
                || addCustStPrvComb.getValue() == null || addCustPstCdTex.getText().isBlank()){

            addCustSvMsgLab.setText(rb.getString("nullValueDetected"));
            return;
        }

        String name = addCustNameTex.getText();
        String address = addCustAddressTex.getText();
        String postal = addCustPstCdTex.getText();
        String phone = addCustPhnTex.getText();
        String steOrProv = String.valueOf(addCustStPrvComb.getValue());
        LocalDateTime createDate = LocalDateTime.now().withNano(0);
        int customerId = CustomersData.getNextCustId();

        int userId = UsersData.getUsrDatUsrId();
        String createdBy = "Script";
        for(capstone.schedulemanager.model.Users user : UsersData.getUsrsList()){
            if(user.getUserId() == userId){
                createdBy = user.getUserName();
            }
        }

        int divId = 0;
        for (capstone.schedulemanager.model.FirstLevelDivisions division : divList){
            if(division.getDivisionName().equals(steOrProv)){
                divId = division.getDivisionId();
            }
        }

        LocalDateTime lastUpdt = createDate;
        String lastUpdtBy = createdBy;

        int check = 0;

        check = CustomersData.insertCust(name, address, postal, phone,createDate,
                createdBy,lastUpdt, lastUpdtBy, divId, customerId);


        if (check > 0){
            helpers.createUserCreateReport();
            helpers.saveSuccessMessage(rb.getString("custSvdMessg"));
        }

        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("/capstone/schedulemanager/view/AddCustomer.fxml"));
            Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load(), 860, 580);
            stage.setScene(scene);
            stage.show();
        }
        catch(IOException ioe){
            ioe.printStackTrace();
            helpers.pageLoadErrMsg();
        }

    }

    /** This method controls the cancel button functionality. When users click this button the Add Customer
     * page is closed and the Main Menu page is opened and initialized.
     * The action event is the pressing of the Cancel button.
     * @param actionEvent Cancel button*/
    @FXML
    protected void onAddCustCancButClick(ActionEvent actionEvent) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("/capstone/schedulemanager/view/MainMenu.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
            stage.setScene(scene);
            stage.show();
        }
        catch(IOException ioe){
            helpers.pageLoadErrMsg();
        }
    }

    /** This method controls the Update button functionality. After selecting a customer from the Customers table and
     * clicking the Update button, the Add Customer form page is closed and the Update Customer for page is opened
     * populated with the selected customer's data.
     * The action event is the pressing of the Update button.
     * @param actionEvent Update button
     * */
    @FXML
    protected void onAddCustUpdtCustClick(ActionEvent actionEvent) {
        capstone.schedulemanager.model.Customers selectedCustomer = (capstone.schedulemanager.model.Customers)addCustomerTable.getSelectionModel().getSelectedItem();

        if(selectedCustomer == null){
            addCustSvMsgLab.setText(rb.getString("nullSelectionDetected"));
            return;
        }

        UpdateCustomerController.setCustToUpdt(selectedCustomer);

        FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("/capstone/schedulemanager/view/UpdateCustomer.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 965, 625);
        } catch(IOException ioe){
            helpers.pageLoadErrMsg();
        }
        stage.setScene(scene);
        stage.show();
    }

    /** This method controls the Delete button functionality. After selecting a customer from the Customers table
     * and clicking the delete button, the customer is prompted with a confirmation message. If not confirmed, no
     * changes are made to the customers table on the database. If confirmed and the customer has no scheduled
     * appointments, the selected customer is deleted from the database. If a customer to be deleted has scheduled
     * appointments a confirmation message is sent where if confirmed the appointments are deleted. Each
     * appointment deleted prompts the customer with an information alert with the appointment ID and type.
     * After the appointments are deleted, the selected customer is deleted from the customers table on the database.
     * After the customer is deleted, the user is prompted with an information message indicating the customer's ID
     * and their name. After the deletion process is completed the customers appointments table is updated with
     * the new customer table data and the next customer ID is updated in the customer ID text field.*/
    @FXML
    protected void onAddCustDelButClick(){

        int appsCounter = 0;
        int custCounter = 0;
        ObservableList<capstone.schedulemanager.model.Appointments> aptList = AppointmentsData.getAptList();

        capstone.schedulemanager.model.Customers selectedCustomer = (capstone.schedulemanager.model.Customers)addCustomerTable.getSelectionModel().getSelectedItem();

        if(selectedCustomer == null){
            addCustSvMsgLab.setText(rb.getString("nullSelectionDetected"));
            return;
        }


        for(int i = 0; i < aptList.size(); ++i){
            if(aptList.get(i).getCustomerId() == selectedCustomer.getCustomerId()){
                appsCounter++;
            }
        }

        if(appsCounter > 0){

            Optional<ButtonType> choice = helpers.getDelConfirm("deleteCustWtAptConfirm");

            int aptCheckCntr = 0;

            if(choice.isPresent() && choice.get() == ButtonType.OK){

                for(int i = 0; i < aptList.size(); ++i){

                    capstone.schedulemanager.model.Appointments aptToDel = aptList.get(i);

                    if(aptList.get(i).getCustomerId() == selectedCustomer.getCustomerId()){
                        aptCheckCntr = AppointmentsData.deleteAppointment(aptList.get(i).getAppointmentId());
                    }
                    if (aptCheckCntr > 0){
                        helpers.getAptDeleteSucsMesg(aptToDel);
                    }
                }

                custCounter = CustomersData.deleteCustomer(selectedCustomer.getCustomerId());

            }
        }
        else{
            Optional<ButtonType> choice = helpers.getDelConfirm("deleteCust");
            if(choice.isPresent() && choice.get() == ButtonType.OK){
                custCounter = CustomersData.deleteCustomer(selectedCustomer.getCustomerId());
            }
        }

        if(custCounter > 0){
            helpers.createUserDeleteReport();
            helpers.getCustDeleteSucsMesg(selectedCustomer);
        }
        else{
            addCustSvMsgLab.setText(rb.getString("sqlConnErrStmt"));
        }

        addCustCustIdTex.setText(String.valueOf(CustomersData.getNextCustId()));

        customers = CustomersData.getCustList();

        addCustomerTable.setItems(customers);
    }

}
