module com {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens com.client.controller to javafx.fxml;
    exports com.client.controller;

    opens com.client.model to javafx.fxml;
    exports com.client.model;

    opens com.server.controller to javafx.fxml;
    exports com.server.controller;
}