package capstone.schedulemanager.controller;

import capstone.schedulemanager.dao.UsersData;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import capstone.schedulemanager.model.Users;
import capstone.schedulemanager.Driver;
import capstone.schedulemanager.utilities.helpers;

import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

/** This class controls the functionality of the User Login page. Users login at this page to access the application's
 * core scheduling functionalities.*/
public class UserLoginController implements Initializable {

    public Button loginMenuLoginButton;
    public TextField loginUsernameText;
    public PasswordField loginPasswordText;
    public Label loginZoneLabel;
    public Label loginPwdLab;
    public Label loginAuthenLab;
    public Label loginLogTitLab;
    public Label loginPwrdMesgLab;
    public Label loginUsrTitLab;
    public Label loginZnIdTitLab;
    ResourceBundle rb = ResourceBundle.getBundle("Languages", Locale.getDefault());

    /** This method initializes the Login form page. The labels can be initialized with either English or French
     * language versions based on the user's language preference. The local time zone of the user is also populated
     * next to the zone ID label.
     * @param url The url provides paths for resources utilized on the Login form page.
     * @param resourceBundle The resources are what is used to localize the Login features and functionalities.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        loginLogTitLab.setText(rb.getString("loginLoginTitLab"));

        loginUsrTitLab.setText(rb.getString("loginUsernameLab"));

        loginPwdLab.setText(rb.getString("loginPasswordLab"));

        loginMenuLoginButton.setText(rb.getString("loginLoginBut"));

        loginZoneLabel.setText(String.valueOf(ZoneId.systemDefault()));

        loginZnIdTitLab.setText(rb.getString("loginZoneIdLab"));

        loginPwrdMesgLab.setText("");
    }

    /** This method is used to control the Login button functionality. After users enter a username and password in
     * the text fields and click the login button the information is cross-referenced with user database data. If the
     * information matches database data for a user, the login page closes and the Menu page loads. If the information
     * provided is invalid, the user is prompted with a message stating that either the username or the password are
     * invalid. Both failed and successful login attempts add a log to the login_activity.txt file. If validated, the
     * information logged includes the login status, user ID, time and date of the login. If not validated. The logged
     * information includes the login status, the time, and the date. The local time zone of the user is displayed at
     * the bottom of the login page.
     * The actionEvent is the clicking of the Login button
     * @param actionEvent Login button*/
    @FXML
    protected void onLoginButtonClick(ActionEvent actionEvent) {

        boolean logonValid = false;

        String username = loginUsernameText.getText();
        String password = loginPasswordText.getText();
        String userNameToCheck = "";

        ArrayList<capstone.schedulemanager.model.Users>users = UsersData.getUsrsList();

        for(capstone.schedulemanager.model.Users user : users){

            userNameToCheck = user.getUserName();
            String passwordToCheck = user.getPassword();

            if(userNameToCheck.equals(username) && passwordToCheck.equals(password)){

                UsersData.setUsrDatUsrId(user.getUserId());

                logonValid = true;

                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("/capstone/schedulemanager/view/MainMenu.fxml"));
                    Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
                    stage.setScene(scene);
                    stage.show();
                }
                catch(IOException ioe){
                    ioe.printStackTrace();
                    helpers.pageLoadErrMsg();
                }
            }
            else{
                loginPwrdMesgLab.setText(rb.getString("loginIvldEntryMsg"));
            }

        }

        String logonData = "";
        String fileName = "login_activity.txt";
        int userId = UsersData.getUsrDatUsrId();
        String userName = UsersData.getUsername(userId);
        LocalDateTime currentDateTime = LocalDateTime.now().withNano(0);

        if(logonValid){
            logonData = rb.getString("loginTxtFlVldPt1") + userId + rb.getString("loginTxtFlVldPt3") +
                    userName + rb.getString("loginTxtFlVldPt2")  + currentDateTime + "\n";
        }
        else{
            logonData = rb.getString("loginTxtFlInvld") + currentDateTime + "\n";
        }

        try (FileWriter writer = new FileWriter(fileName, true)) {

            writer.write(logonData);

        } catch (IOException e) {
            e.printStackTrace();
            helpers.pageLoadErrMsg();}
    }
}
