package com.server.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.util.List;

public class Server {
    public List<String> emailsAddress;
    public static final String LOG="<>Ciao questo e' un log";
    @FXML
    TextArea logArea;

    @FXML
    public void initialize(){
    logArea.setText(LOG);
    }



}
