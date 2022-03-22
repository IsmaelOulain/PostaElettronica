module com.server.server {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.server.controller to javafx.fxml;
    exports com.server.controller;
}