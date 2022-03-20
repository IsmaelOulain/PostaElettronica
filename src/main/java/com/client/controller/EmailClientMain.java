package com.client.controller;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class EmailClientMain extends Application {

    @FXML
    TextField email;

    @Override
    public void start(Stage stage) throws IOException {
        URL clientUrl = EmailClientMain.class.getResource("login.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(clientUrl);
        Scene scene = new Scene(fxmlLoader.load(), 250, 150);
        stage.setTitle("Email client");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}