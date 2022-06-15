package com.server.controller;

import com.client.model.Email;
import com.server.model.Response;
import javafx.scene.control.TextArea;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ThreadPoolExecutor;

public class ServerConn implements  Runnable{

	Socket socket = null;
	ObjectInputStream inStream = null;
	ObjectOutputStream outStream = null;
	TextArea logArea;
	ThreadPoolExecutor executor;
	public static final String PROPERTIES="C:\\Users\\oulai\\Documents\\GitHub\\PostaElettronica\\server\\src\\main\\resources\\address.properties";
	private String log;

	public ServerConn(TextArea logArea, ThreadPoolExecutor executor) {
		this.logArea=logArea;
		this.executor= executor;
	}

	/**
	 * Il server si mette in ascolto su una determinata porta e serve i client.
	 * I messaggi ricevuti dai client vengono stampati a video, modificati e inviati
	 * nuovamente al mittente.
	 *
	 * NB: Questa implementazione del server usa un solo thread, NON è un'implementazione
	 * scalabile, NON è sufficiente ai fini del progetto.
	 *
	 * @param port la porta su cui è in ascolto il server.
	 */
	public void listen(int port) {
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			logArea.appendText("hai premuto accendi\n");
			while (true) {
				serveClient(serverSocket);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket!=null)
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}




	// Gestisce un singolo client
	private void serveClient(ServerSocket serverSocket) {
		try {
			openStreams(serverSocket);//attendo la connessione

//			String mail = (String) inStream.readObject();
//			logArea.appendText(mail+" si e connesso\n");
//			//updateStudents(mail);
//
//			outStream.writeObject(updateStudents(mail));
//			outStream.flush();

		} catch (IOException  e) {
			e.printStackTrace();
		} finally {
			closeStreams();
		}
	}

	// Chiude gli stream utilizzati durante l'ultima connessione
	private void closeStreams() {
		try {
			if(inStream != null) {
					inStream.close();
			}

			if(outStream != null) {
				outStream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Aggiorna i dati studente passati in input
//	private List<Email> updateStudents(String mail) {
//		if (mail != null) {
//				System.out.println("Oggetto ricevuto => " + mail);
//				if (mail.equals("ismael@gmail.com")){
//					return readCasella(mail);
//				}
//
//
//		}
//		return null;
//	}

//	private List<Email> readCasella(String emailAddress) {
//		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//		try {
//
//			DocumentBuilder db = dbf.newDocumentBuilder();
//
//			Document doc = db.parse(new File("C:\\Users\\oulai\\Documents\\GitHub\\PostaElettronica\\server\\src\\main\\resources\\caselle\\ismael\\xmlfile.xml"));
//
//			// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
//			doc.getDocumentElement().normalize();
//
//			System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
//			System.out.println("------");
//
//			// get <Utente>
//			NodeList list = doc.getElementsByTagName("Utente");
//
//			for (int temp = 0; temp < list.getLength(); temp++) {
//
//				Node node = list.item(temp);
//				Element element = (Element) node;
//
//				// get utente id
//				String id = element.getAttribute("id");
//				if (node.getNodeType() == Node.ELEMENT_NODE && id.equals(emailAddress)) {
//					NodeList mails = element.getElementsByTagName("Mail");
//					List<Email> inbox = new ArrayList<>();
//					for (int c = 0; c < mails.getLength(); c++) {
//						Node email = mails.item(c);
//						Element posta = (Element) email;
//						String data = posta.getAttribute("dataInvio");
//						String mailFrom = posta.getElementsByTagName("MailFrom").item(0).getTextContent();
//						String mailTo = posta.getElementsByTagName("MailTo").item(0).getTextContent();
//						List<String> senders = Arrays.asList(mailTo.split(","));
//						String mailOggetto = posta.getElementsByTagName("MailOggetto").item(0).getTextContent();
//						String mailMsg = posta.getElementsByTagName("MailMsg").item(0).getTextContent();
//						inbox.add(new Email(mailFrom,senders,mailOggetto,mailMsg,data));
//					}
//					return inbox;
//				}
//			}
//
//		} catch (ParserConfigurationException | SAXException | IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

	// apre gli stream necessari alla connessione corrente
	private void openStreams(ServerSocket serverSocket) throws IOException {
		socket = serverSocket.accept();//ricevo la connessione
		ClientHandler cl = new ClientHandler(socket,logArea);//delego la connessione
		executor.execute(cl);
//		logArea.appendText( "ciao sono connesso\n");
//		inStream = new ObjectInputStream(socket.getInputStream());
//		outStream = new ObjectOutputStream(socket.getOutputStream());
//		outStream.flush();
	}

//	public static void main(String[] args) {
//		System.out.println("Sever startato...");
//		ServerConn server = new ServerConn();
//		server.listen(4445);
//	}

	@Override
	public void run() {
		listen(4445);
	}
}
class ClientHandler implements  Runnable{
	Socket s;
	TextArea logArea;
	ObjectInputStream inStream = null;
	ObjectOutputStream outStream = null;
	public static final String PROPERTIES="C:\\Users\\oulai\\Documents\\GitHub\\PostaElettronica\\server\\src\\main\\resources\\address.properties";
	public ClientHandler(Socket s,TextArea logArea) {
		this.s=s;
		this.logArea=logArea;
	}

	@Override
	public void run() {
		logArea.appendText( "ciao sono connesso\n");
		try {
			inStream = new ObjectInputStream(s.getInputStream());
			outStream = new ObjectOutputStream(s.getOutputStream());
			outStream.flush();

			String mail = (String) inStream.readObject();
			if(checkEmail(mail)){
				outStream.writeObject(updateStudents(mail));
				logArea.appendText(mail+" si e connesso correttamente\n");
			}

			//updateStudents(mail);


			outStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		//openStreams(s);
	}

	public boolean checkEmail(String email){
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(PROPERTIES));
			prop.containsKey(email);
			prop.getProperty(email);
			logArea.appendText("utente "+email+" esiste\n");
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	private void closeStreams() {
		try {
			if(inStream != null) {
				inStream.close();
			}

			if(outStream != null) {
				outStream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Aggiorna i dati studente passati in input
	private Response updateStudents(String mail) {
		Response res= new Response();
		System.out.println("Oggetto ricevuto => " + mail);
		List<Email> listEmail =readCasella(mail);
		if(listEmail!=null && !listEmail.isEmpty()){
			res.setEmails(listEmail);
			res.setStato(Response.STATUS.SUCCESS);
		}

		return res;
	}

	private List<Email> readCasella(String emailAddress) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {

			DocumentBuilder db = dbf.newDocumentBuilder();

			Document doc = db.parse(new File("C:\\Users\\oulai\\Documents\\GitHub\\PostaElettronica\\server\\src\\main\\resources\\caselle\\ismael\\xmlfile.xml"));

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
				if (node.getNodeType() == Node.ELEMENT_NODE && id.equals(emailAddress)) {
					NodeList mails = element.getElementsByTagName("Mail");
					List<Email> inbox = new ArrayList<>();
					for (int c = 0; c < mails.getLength(); c++) {
						Node email = mails.item(c);
						Element posta = (Element) email;
						String data = posta.getAttribute("dataInvio");
						String mailFrom = posta.getElementsByTagName("MailFrom").item(0).getTextContent();
						String mailTo = posta.getElementsByTagName("MailTo").item(0).getTextContent();
						List<String> senders = Arrays.asList(mailTo.split(","));
						String mailOggetto = posta.getElementsByTagName("MailOggetto").item(0).getTextContent();
						String mailMsg = posta.getElementsByTagName("MailMsg").item(0).getTextContent();
						inbox.add(new Email(mailFrom,senders,mailOggetto,mailMsg,data));
					}
					return inbox;
				}
			}

		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}


}