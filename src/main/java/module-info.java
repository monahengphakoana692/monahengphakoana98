module com.example.limkokwingsystemmanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires mongo.java.driver;
    requires java.desktop;
    requires java.management;
    requires jbcrypt;
    requires java.sql;


    opens com.example.limkokwingsystemmanagement to javafx.fxml;
    exports com.example.limkokwingsystemmanagement;
}