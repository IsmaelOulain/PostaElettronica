package com.client.controller;


import com.client.model.Client;
import com.client.model.Email;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.time.format.SignStyle;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

/**
 * Classe Controller 
 */

public class ClientController {
    @FXML
    private TextField lblFrom;

    @FXML
    private TextField lblTo;

    @FXML
    private TextField lblSubject;

    @FXML
    private Label lblUsername;

    @FXML
    private TextArea txtEmailContent;

    @FXML
    BorderPane pnlReadMessage;

    @FXML
    Button btnRispondi;

    @FXML
    Button btnRispondiAll;

    @FXML
    Button btnInoltra;

    @FXML
    Button logout;
    

    @FXML
    private ListView<Email> lstEmails;

    private Client model;
    private Email selectedEmail;
    private Email emptyEmail;

    @FXML
    public void initialize(Client model){
        if (this.model != null) throw new IllegalStateException("Model can only be initialized once");
        //qui creaiamo una connessione al server
        //il server riceve il nome del client
        //il server verifica se l'utente esiste
        //il server legge dal file xml le mail dell'utente
        //il server restituisce un oggetto contente le mail dell'utente
        //istanza nuovo client

           this.model=model;

                //model.generateRandomEmails(10);

                selectedEmail = null;
                if(model!=null){
                    //binding tra lstEmails e inboxProperty
                    lstEmails.itemsProperty().bind(model.inboxProperty());
                    lstEmails.setOnMouseClicked(ClientController.this::showSelectedEmail);
                    lblUsername.textProperty().bind(model.emailAddressProperty());
                    emptyEmail = new Email("", Arrays.asList(""), "", "","");
                    if(lstEmails.getItems().isEmpty()){
                        updateDetailView(emptyEmail);
                    }else  updateDetailView(lstEmails.getItems().get(0));
                }else System.out.println("Ricevuto model nullo");



            }




    /**
     * Elimina la mail selezionata
     */
    @FXML
    protected void onDeleteButtonClick() {
        model.deleteEmail(selectedEmail);
        updateDetailView(emptyEmail);
    }

    @FXML
    protected void onWriteButtonClick(){
        emptyEmail = new Email("", Arrays.asList(""), "", "","");
        updateDetailView(emptyEmail);
        lblFrom.setEditable(true);
        lblTo.setEditable(true);
        lblSubject.setEditable(true);
        txtEmailContent.setEditable(true);
        btnInoltra.setVisible(false);
        btnRispondi.setVisible(false);
        btnRispondiAll.setVisible(false);

    }

     /**
     * Mostra la mail selezionata nella vista
     */
    protected void showSelectedEmail(MouseEvent mouseEvent) {
        Email email = lstEmails.getSelectionModel().getSelectedItem();
        lblFrom.setEditable(false);
        lblTo.setEditable(false);
        lblSubject.setEditable(false);
        txtEmailContent.setEditable(false);
        btnInoltra.setVisible(true);
        btnRispondi.setVisible(true);
        btnRispondiAll.setVisible(true);
        selectedEmail = email;
        updateDetailView(email);
    }

     /**
     * Aggiorna la vista con la mail selezionata
     */
    protected void updateDetailView(Email email) {
        if(email != null) {
            lblFrom.setText(email.getSender());
            lblTo.setText(String.join(",", email.getReceivers()));
            lblSubject.setText(email.getSubject());
            txtEmailContent.setText(email.getText());
            txtEmailContent.setEditable(false);
        }
    }
    /**
     * Eseguo il logout
     */
    public void userLogout(ActionEvent event) throws IOException {
        SceneController m = new SceneController();
        m.switchToScene1(event);
    }


}
