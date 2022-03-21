package com.server.controller;

import com.client.controller.EmailClientMain;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class ServerApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        URL clientUrl = ServerApp.class.getResource("server.fxml");
        System.out.println(getClass().getResource("com.server/controller/server.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(clientUrl);
        Scene scene = new Scene(fxmlLoader.load(), 250, 100);
        stage.setTitle("Server App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
