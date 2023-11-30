package capstone.schedulemanager.controller;

import capstone.schedulemanager.dao.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import capstone.schedulemanager.Driver;
import capstone.schedulemanager.model.*;
import capstone.schedulemanager.utilities.Element;
import capstone.schedulemanager.utilities.helpers;

import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/** This class controls the functionality of the Reports page. This page provides three reports. The report of total
 * appointments by type can be filtered by month through the month combo box at the top of the report table.
 * The appointments by contact can be filtered through the selection of a contact from the contact combo box above the
 * appointments by contact table. The user productivity report is initialized upon access of the page. This report shows
 * user create and update activity totals for all appointments on the appointments table.*/
public class ReportsController implements Initializable {

    public ComboBox repMonCom;
    public TableView repAptMonTotTab;
    public TableColumn repMonTotTypCol;
    public TableColumn repMonTotTotCol;
    public ComboBox repAptByConConCom;
    public TableView repAptByConTab;
    public TableColumn repAptByConAptIdCol;
    public TableColumn repAptByConTitCol;
    public TableColumn repAptByConTypCol;
    public TableColumn repAptByConDescCol;
    public TableColumn repAptByConStrtCol;
    public TableColumn repAptByConEndCol;
    public TableColumn repAptByConCstNmCol;
    public TableView repAptUsrProdTab;
    public TableColumn repAptUsrIdCol;
    public TableColumn repAptUsrNmCol;
    public TableColumn repAptUsrCrtCol;
    public TableColumn repAptUsrUpdtCol;
    public TableColumn repAptByConCustIdCol;
    public Button repAptUsrMenuBut;
    public TableColumn repAptUsrDeleteCol;
    ObservableList<capstone.schedulemanager.model.Appointments> appointments = AppointmentsData.getAptList();

    /** Declaration of a Lambda expression used to extract the month number from a date as a String type.
     * @return the month number as a String type*/
    Element monthNum = date -> date.split("-")[1];

    /** This method initializes the Reports page. The month combo box is populated with the months that appointments
     * are scheduled, the Contacts combo box is populated with the contact names, and the User Productivity table is
     * populated with user productivity data.
     * LAMBDA EXPRESSION 2 (getElement()): Extracts an element from a String. This was made for the reuse and
     * simplification of code.
     * @param location The url provides paths for resources utilized on the Reports page.
     * @param resources The resources are what is used to localize the Reports features and functionalities.*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<UserProductivityReport> usrProdList = FXCollections.observableArrayList();

        for(int i = 0; i < appointments.size(); ++i){

            /** Lambda used to extract the month number from an appointment's start date and time.*/
            String monthToCheck = monthNum.getElement(appointments.get(i).getStartDateAndTime().toLocalDate().toString());
            String monthName = helpers.getCldrMonthName(monthToCheck);
            if(!repMonCom.getItems().contains(monthName)){
                repMonCom.getItems().add(monthName);
            }
        }

        for(capstone.schedulemanager.model.Contacts contact : ContactsData.getCtsList()){
            String name = contact.getContactName();
            if(!repAptByConConCom.getItems().contains(name)){
                repAptByConConCom.getItems().add(name);
            }
        }

        makeUsrProductivityList(usrProdList);

        repAptUsrProdTab.setItems(usrProdList);
        repAptUsrIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        repAptUsrNmCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        repAptUsrCrtCol.setCellValueFactory(new PropertyValueFactory<>("createdTotal"));
        repAptUsrUpdtCol.setCellValueFactory(new PropertyValueFactory<>("updatedTotal"));
        repAptUsrDeleteCol.setCellValueFactory(new PropertyValueFactory<>("deletedTotal"));

    }

    /** This method creates an observable list of appointment totals by month and type. A String month number is input
     * and an observable list of that month's total appointments per type is output.
     * LAMBDA EXPRESSION 2 (getElement()): Extracts an element from a String. This was made for the reuse and
     * simplification of code.
     * The month number input is the number of the month whose appointment totals are being checked.
     * @param monthNumInput String month number
     * @return aptsTotByMons The list of the month's totals*/
    public ObservableList<capstone.schedulemanager.model.ReportAptsTotByMon> getTypesTotals(String monthNumInput){

        ObservableList<capstone.schedulemanager.model.ReportAptsTotByMon> aptsTotByMons = FXCollections.observableArrayList();
        ArrayList<String> types = new ArrayList<>();
        Map<String, Integer > typeCount = new HashMap<>();

        for(int i = 0; i < appointments.size(); ++i){

            /** Lambda used to extract the month number from an appointment's start date and time.*/
            String monthToCheck = monthNum.getElement(appointments.get(i).getStartDateAndTime().toLocalDate().toString());
            if(monthToCheck.equals(monthNumInput)){
                if(!types.contains(appointments.get(i).getType())){
                    types.add(appointments.get(i).getType());
                }
            }
        }

        for(int i = 0; i < appointments.size(); ++i){

            /** Lambda used to extract the month number from an appointment's start date and time.*/
            String monthToCheck = monthNum.getElement(appointments.get(i).getStartDateAndTime().toLocalDate().toString());
            if(monthToCheck.equals(monthNumInput)){
                for(int j = 0; j < types.size(); ++j){
                    String type = types.get(j);
                    if(appointments.get(i).getType().equals(type)){
                        if(typeCount.containsKey(type)){
                            int newVal = typeCount.get(types.get(j)) + 1;
                            typeCount.put(type, newVal);
                        }
                        else{
                            typeCount.put(type, 1);
                        }
                    }
                }
            }
        }

        for(Map.Entry<String, Integer> counts : typeCount.entrySet()){
            aptsTotByMons.add(new capstone.schedulemanager.model.ReportAptsTotByMon(counts.getKey(), counts.getValue()));
        }

        return aptsTotByMons;
    }

    /** This method outputs a list of total appointments by type based on the month selected. A user selects a month
     * from the combo box and that month's list of appointments by type is output onto the table.*/
    @FXML
    protected void onRepMonComClick(){
        String month = helpers.getClndMonthNum(String.valueOf(repMonCom.getValue()));

        ObservableList<capstone.schedulemanager.model.ReportAptsTotByMon> list = getTypesTotals(month);

        repAptMonTotTab.setItems(list);
        repMonTotTypCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        repMonTotTotCol.setCellValueFactory(new PropertyValueFactory<>("total"));
    }

    /** This method creates and outputs a list of the appointments scheduled for the selected contact. After a user
     * selects a contact from the combo box a list is created of all that contact's scheduled appointments. The list
     * is then output onto the table.*/
    @FXML
    protected  void onRepAptByConConComClick(){

        ArrayList<capstone.schedulemanager.model.AptWithName> aptsWtNms = new ArrayList<>();
        ObservableList<capstone.schedulemanager.model.AptWithName> contactScheduleList = FXCollections.observableArrayList();

        String contName = String.valueOf(repAptByConConCom.getValue());

        int contId = ContactsData.getContactId(contName);

        for(int i = 0; i < appointments.size(); ++i){
            int aptId = appointments.get(i).getAppointmentId();
            String title = appointments.get(i).getTitle();
            String dscrp = appointments.get(i).getDescription();
            String loc = appointments.get(i).getLocation();
            String type = appointments.get(i).getType();
            LocalDateTime strtDtTime = appointments.get(i).getStartDateAndTime();
            LocalDateTime endDtTime = appointments.get(i).getEndDateAndTime();
            LocalDateTime crtDt = appointments.get(i).getCreateDate();
            String crtBy = appointments.get(i).getCreatedBy();
            LocalDateTime lstUpdt = appointments.get(i).getLastUpdate();
            String lstUpdtBy = appointments.get(i).getLastUpdateBy();
            int cstId = appointments.get(i).getCustomerId();
            int cntId = appointments.get(i).getContactId();
            int usrId = appointments.get(i).getUserId();
            String custName = CustomersData.getCustomerName(cstId);
            String cntName = ContactsData.getContactName(cntId);
            aptsWtNms.add(new AptWithName(aptId, title, dscrp, loc, type,
                    strtDtTime, endDtTime, crtDt, crtBy, lstUpdt, lstUpdtBy, cstId,
                    usrId, cntId, custName, cntName));
        }

        for(int i = 0; i < aptsWtNms.size(); ++i){
            if(aptsWtNms.get(i).getContactId() == contId){
                contactScheduleList.add(aptsWtNms.get(i));
            }
        }

        repAptByConTab.setItems(contactScheduleList);
        repAptByConAptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        repAptByConTitCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        repAptByConTypCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        repAptByConDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        repAptByConStrtCol.setCellValueFactory(new PropertyValueFactory<>("startDateAndTime"));
        repAptByConEndCol.setCellValueFactory(new PropertyValueFactory<>("endDateAndTime"));
        repAptByConCstNmCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        repAptByConCustIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    }

    /** This method is used to make a productivity list of user activity regarding updates and creates of appointments
     *  made on the database. The username is checked for each appointment. If that name matches the currently
     *  referenced username, 1 is added to that user's counter of productivity type.
     *  The list is the observable list to be updated with productivity data.
     *  @param list Observable list*/
    public void makeUsrProductivityList(ObservableList<UserProductivityReport> list){

        Map<Integer, Integer> createdMapTotal = new HashMap<>();
        Map<Integer, Integer> updatedMapTotal = new HashMap<>();
        Map<Integer, Integer> deletedMapTotal = new HashMap<>();

        UsersData.updateUserProductivityCounts(createdMapTotal,updatedMapTotal,deletedMapTotal);


        ArrayList<Users> users = UsersData.getUsrsList();
        for (Users user : users){

            int createdTotal=0, updatedTotal=0, deletedTotal=0;

            if(createdMapTotal.containsKey(user.getUserId())){

                for (Map.Entry<Integer, Integer> createdEntry : createdMapTotal.entrySet()){

                    if(user.getUserId() == createdEntry.getKey()){
                        createdTotal = createdEntry.getValue();
                    }
                }
            }

            if(updatedMapTotal.containsKey(user.getUserId())){

                for (Map.Entry<Integer, Integer> updatedEntry : updatedMapTotal.entrySet()){

                    if(user.getUserId() == updatedEntry.getKey()){
                        updatedTotal = updatedEntry.getValue();
                    }
                }
            }

            if(deletedMapTotal.containsKey(user.getUserId())){

                for (Map.Entry<Integer,Integer> deletedEntry : deletedMapTotal.entrySet()){

                    if(user.getUserId() == deletedEntry.getKey()){

                        deletedTotal = deletedEntry.getValue();
                    }
                }
            }

            list.add(new UserProductivityReport(user.getUserId(), user.getUserName(),
                    createdTotal, updatedTotal,deletedTotal));
        }


    }

    /** This method closes the Reports page and loads the Menu page. After the user clicks the menu button, the Reports
     * page is closed and the Menu page is loaded.
     * The actionEvent is the pressing of the Menu button.
     * @param actionEvent Menu button*/
    @FXML
    protected void onRepAptUsrMenuBut(ActionEvent actionEvent) {

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

}
