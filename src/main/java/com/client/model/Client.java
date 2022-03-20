package com.client.model;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Classe Client, conterrà la lista di mail che sarà il model
 */

public class Client {
    private final ListProperty<Email> inbox;
    private final ObservableList<Email> inboxContent;
    private final StringProperty emailAddress;
    private static final String FILENAME = "src/main/resources/com/client/model/xmlfile.xml";

    /**
     * Costruttore della classe.
     *
     * @param emailAddress   indirizzo email    
     *
     */

    public Client(String emailAddress) {
        this.inboxContent = FXCollections.observableList(new LinkedList<>());
        this.inbox = new SimpleListProperty<>();
        this.inbox.set(inboxContent);
        this.emailAddress = new SimpleStringProperty(emailAddress);
    }

    /**
     * @return      lista di email  
     *
     */
    public ListProperty<Email> inboxProperty() {
        return inbox;
    }

    /**
     *
     * @return   indirizzo email della casella postale   
     *
     */
    public StringProperty emailAddressProperty() {
        return emailAddress;
    }

    /**
     *
     * @return   elimina l'email specificata   
     *
     */
    public void deleteEmail(Email email) {
        inboxContent.remove(email);
    }

    /**
     *genera email random da aggiungere alla lista di email, ese verranno mostrate nella ui
     */
    public void generateRandomEmails(int n) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {

            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new File(FILENAME));

            // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
            System.out.println("------");

            // get <Utente>
            NodeList list = doc.getElementsByTagName("Utente");

            for (int temp = 0; temp < list.getLength(); temp++) {

                Node node = list.item(temp);
                Element element = (Element) node;

                // get utente id
                String id = element.getAttribute("id");
                if (node.getNodeType() == Node.ELEMENT_NODE && id.equals(emailAddress.getValue())) {
                    NodeList mails = element.getElementsByTagName("Mail");
                    for (int c = 0; c < mails.getLength(); c++) {
                        Node email = mails.item(c);
                        Element posta = (Element) email;
                        String data = posta.getAttribute("dataInvio");
                        String mailFrom = posta.getElementsByTagName("MailFrom").item(0).getTextContent();
                        String mailTo = posta.getElementsByTagName("MailTo").item(0).getTextContent();
                        List<String> senders = Arrays.asList(mailTo.split(","));
                        String mailOggetto = posta.getElementsByTagName("MailOggetto").item(0).getTextContent();
                        String mailMsg = posta.getElementsByTagName("MailMsg").item(0).getTextContent();
                        inboxContent.add(new Email(mailFrom,senders,mailOggetto,mailMsg,data));
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }



}

