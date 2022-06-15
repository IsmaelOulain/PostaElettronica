package com.client.controller;

import com.client.model.Client;
import com.client.model.Email;
import com.server.model.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.List;

public class ClientConn {
	Socket socket = null;
	ObjectOutputStream outputStream = null;
	ObjectInputStream inputStream = null;
	String mail;

	final int MAX_ATTEMPTS = 5;

	/**
	 * Costruisce un nuovo client.
	 * @param mail identificatore numerico, utile solamente per la stampa dei messaggi.
	 */
	public ClientConn(String mail) {
		this.mail = mail;
	}

	/**
	 * Fa fino a 5 tentativi per comunicare con il server. Dopo ogni tentativo fallito
	 * aspetta 1 secondo.
	 * @param host l'indirizzo sul quale il server è in ascolto.
	 * @param port la porta su cui il server è in ascolto.
	 */
	public Client communicate(String host, int port){
		int attempts = 0;

		Client client = null;

			attempts += 1;
			System.out.println("[Client "+ this.mail +"] Tentativo nr. " + attempts);

			client = tryCommunication(host, port);//qui dove avviene la comunicazione
			Boolean success = client !=null;
			if(success) {
				return client;
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		return null;
	}

	// Tenta di comunicare con il server. Restituisce true se ha successo, false altrimenti
	private Client tryCommunication(String host, int port) {
		try {
			connectToServer(host, port);
			//List<Email> client = new ArrayList<Email>();//generateStudents(3);
			//client.add(new Email(mailFrom,senders,mailOggetto,mailMsg,data));
			//Thread.sleep(5000);
			//invio il nome del client connesso
			sendStudents(mail);
			return receiveInbox();

		} catch (ConnectException ce) {
			// nothing to be done
		} catch (IOException | ClassNotFoundException se) {
			se.printStackTrace();

		}  finally {

			closeConnections();
		}
		return null;
	}

	private void closeConnections() {
		if (socket != null) {
			try {
				inputStream.close();
				outputStream.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void sendStudents(String account) throws IOException, ClassNotFoundException {
		outputStream.writeObject(account);
		outputStream.flush();
	}

	private Client receiveInbox() throws IOException, ClassNotFoundException {
		Response res =  (Response)inputStream.readObject();
		if(res.getStato().equals(Response.STATUS.SUCCESS)){
			System.out.println("Ho recuperato la casella : "+res.getStato());
			Client c = new Client(mail);
			c.generateRandomEmails(res.getEmails());
			return c;
		}
		return null;
	}

	private void connectToServer(String host, int port) throws IOException {
		socket = new Socket(host, port);
		outputStream = new ObjectOutputStream(socket.getOutputStream());

		// Dalla documentazione di ObjectOutputStream
		// callers may wish to flush the stream immediately to ensure that constructors for receiving
		// ObjectInputStreams will not block when reading the header.
		outputStream.flush();

		inputStream = new ObjectInputStream(socket.getInputStream());

		System.out.println("[Client "+ this.mail + "] Connesso");
	}

	/**
	 * Genera una lista di Student popolata casualmente.
	 * @param n il numero di studenti.
	 * @return una lista contenente gli studenti.
	 */
//	private List<Student> generateStudents(int n) {
//		List<Student> students = new ArrayList<>();
//		Random r = new Random(System.currentTimeMillis());
//
//		String[] names = {"Mario Bianchi", "Marco Rossi", "Andrea Gialli", "Sara Verdi", "Giulia Neri"};
//		String[] descriptions = {"Primo anno", "Secondo anno", "Terzo anno"};
//
//		for (int i = 0; i < n; i++)
//			students.add(new Student(i, names[r.nextInt(names.length)], descriptions[r.nextInt(descriptions.length)]));
//
//		return students;
//	}
}