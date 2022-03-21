package com.client.controller;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class EmailClientMain extends Application {
    private static Stage stg;

    /*@FXML
    TextField email; */ //per ora non serve

    @Override
    public void start(Stage stage) throws IOException {
        stg = stage;
        URL clientUrl = EmailClientMain.class.getResource("login.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(clientUrl);
        Scene scene = new Scene(fxmlLoader.load(), 250, 100);
        stage.setTitle("Mail client");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }
    public void changeScene(String fxml) throws IOException{
        Parent pane = FXMLLoader.load(this.getClass().getResource(fxml));
        stg.setScene(new Scene(pane));
        stg.centerOnScreen();
    }

    public static void main(String[] args) {
        launch();
    }
}