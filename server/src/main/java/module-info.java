module com.server {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires com.client;


    opens com.server.controller to javafx.fxml;
    exports com.server.controller;
    exports com.server.model;
}