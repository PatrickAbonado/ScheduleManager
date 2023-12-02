package capstone.schedulemanager;

import capstone.schedulemanager.dao.JDBC;
import capstone.schedulemanager.utilities.helpers;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Driver extends Application {

    /** This method starts the application and loads the user login page.
     * @param stage The user login page*/
    @Override
    public void start(Stage stage)  {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("/capstone/schedulemanager/view/UserLogin.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 450, 400);
            stage.setScene(scene);
            stage.show();
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    /** The main method opens and closes a connection to the database and launches the application.
     * @param args Application input*/
    public static void main(String[] args) {

        JDBC.openConnection();

        launch();

        JDBC.closeConnection();
    }
}
