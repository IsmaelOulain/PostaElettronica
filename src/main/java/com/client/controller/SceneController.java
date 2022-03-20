package com.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    TextField Email;

    public void switchToScene1(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root,150,200);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToClient(ActionEvent event) throws IOException {
        String email = Email.getText();
        FXMLLoader loader =new FXMLLoader(getClass().getResource("client.fxml"));
        root = loader.load();

        ClientController controller = loader.getController();
        controller.initialize(email);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root,900,900);
        stage.setScene(scene);
        stage.show();
    }




}