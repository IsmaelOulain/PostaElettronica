package com.server.controller;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ServerController {
    public List<String> emailsAddress;
    public static final String LOG="<>Ciao questo e' un log\n";
    public static final String PROPERTIES="C:\\Users\\oulai\\Documents\\GitHub\\PostaElettronica\\server\\src\\main\\resources\\address.properties";
    ThreadPoolExecutor executors = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
    @FXML
    TextArea logArea;
    protected ServerSocket s;
    @FXML
    public void initialize(){
        //logArea.setText(LOG);
        //setLog("Ciao premi accendi\n");
        //startConnessione();
    }



    public void setLog(String log){
        logArea.appendText(log);
    }

    @FXML
    public void startConnessione(){

        executors.execute(new ServerConn(logArea,executors));

      //  Platform.runLater(() -> setLog(conn.getLog()));
    }

    @FXML
    public void close(){
            executors.shutdown();
            setLog("connessione chiusa\n");
    }


}

