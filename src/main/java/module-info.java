module capstone.schedulemanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens capstone.schedulemanager to javafx.fxml;
    exports capstone.schedulemanager;
    exports capstone.schedulemanager.model;
    exports capstone.schedulemanager.dao;
    exports capstone.schedulemanager.controller;
    exports capstone.schedulemanager.utilities;
    opens capstone.schedulemanager.controller to javafx.fxml;
}