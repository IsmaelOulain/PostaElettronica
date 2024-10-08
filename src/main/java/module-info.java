module com.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.server;

    opens com.client.controller to javafx.fxml;
    exports com.client.controller;

    opens com.client.model to javafx.fxml;
    exports com.client.model;


}