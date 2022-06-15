package com.server.model;

import com.client.model.Client;
import com.client.model.Email;

import java.util.List;

public class Response {
    public enum  STATUS{
        USER_NOT_FOUND,
        ERROR,
        CLOSED,
        SUCCESS
    }
    private Enum stato;
    private List<Email> emails;

    public List<Email> getEmails() {
        return emails;
    }

    public void setEmails(List<Email> emails) {
        this.emails = emails;
    }

    public Enum getStato() {
        return stato;
    }

    public void setStato(Enum stato) {
        this.stato = stato;
    }
}
