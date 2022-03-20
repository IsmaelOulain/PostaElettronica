package com.client.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Rappresenta una mail
 */

public class Email {

    private String sender;
    private List<String> receivers;
    private String subject;
    private String text;
    private String data;

    private Email() {}

    /**
     * Costruttore della classe.
     *
     * @param sender     email del mittente
     * @param receivers  emails dei destinatari
     * @param subject    oggetto della mail
     * @param text       testo della mail
     */


    public Email(String sender, List<String> receivers, String subject, String text,String data) {
        this.sender = sender;
        this.subject = subject;
        this.text = text;
        this.receivers = new ArrayList<>(receivers);
        this.data=data;

    }

    public String getSender() {
        return sender;
    }

    public List<String> getReceivers() {
        return receivers;
    }

    public String getSubject() {
        return subject;
    }

    public String getText() {
        return text;
    }

    /**
     * @return      stringa composta dagli indirizzi e-mail del mittente più destinatari
     */
    @Override
    public String toString() {
        return String.join(" - ", Arrays.asList(this.sender,this.subject));
    }
}
