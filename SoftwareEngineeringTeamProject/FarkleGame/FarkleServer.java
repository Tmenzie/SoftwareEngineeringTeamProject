package FarkleGame;

import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

//Author: 			Shandon Probst
//Description:		

public class FarkleServer extends AbstractServer{

	private JTextArea log;
	private JLabel status;
	private Database database;
	
	public FarkleServer() {
		
		super(12345);
		this.setTimeout(500);
		
		this.status = new JLabel();
		this.log = new JTextArea();
		this.database = new Database();
	}
	
	public void setLog(JTextArea log) {
		this.log = log;
	}
	
	public void setStatus(JLabel status) {
		this.status = status;
	}
	
	void setDatabase(Database database) {
		this.database = database;
	}

	public void handleMessageFromClient(Object arg0, ConnectionToClient arg1) {
		// Return message/object
		Object toClient = null;
		
		// PERFORMED IF LOGINDATA IS RECEIVED
		if (arg0 instanceof LoginData) {
			LoginData toVerify = (LoginData) arg0;
			
			// Checks if login data can be verified
			if (database.verifyAccount(toVerify.getUsername(), toVerify.getPassword())) {
		        toClient = "LoginSuccessful";
		        log.append("Client " + arg1.getId() + " successfully logged in as \"" + toVerify.getUsername() + "\"\n");
		    }
			
			// If there is an error, make object an error and set the message and message type
		    else {
		    	toClient = new Error("The username and password are incorrect.", "Login");
		        log.append("Client " + arg1.getId() + " failed to log in\n");
		    }
			
			// Sends message to client
			try {
				arg1.sendToClient(toClient);
			}
			// Pointless exception
		    catch (IOException e) {
		        return;
		    }
		}
		
		else if (arg0 instanceof CreateAccountData) {
			CreateAccountData toCreate = (CreateAccountData) arg0;
			
			// Checks if account can be created
			if (database.createNewAccount(toCreate.getUsername(), toCreate.getPassword())) {
				toClient = "CreateAccountSuccessful";
		        log.append("Client " + arg1.getId() + " created a new account called \"" + toCreate.getUsername() + "\"\n");
		    }
			
			// If there is an error, make object an error and set the message and message type
		    else {
		    	toClient = new Error("Username has already been selected.", "CreateAccount");
		        log.append("Client " + arg1.getId() + " failed to create a new account\n");
		    }
			
			// Sends message to client
			try {
				arg1.sendToClient(toClient);
			}
			// Pointless exception
		    catch (IOException e) {
		        return;
		    }
		}
	}
	
	public void listeningException(Throwable exception) {
		status.setText("<html>Status: <font color='red'>Exception Occurred When Listening</font></html>");
		log.append((exception.getMessage()));
		log.append("Press \"Listen\" to restart server");
	}
	
	public void serverStarted() {
		status.setText("<html>Status: <font color='green'>Listening</font></html>");
		log.append("Server started\n");
	}
	
	public void serverStopped() {
		status.setText("<html>Status: <font color='red'>Stopped</font></html>");
		log.append("Server stopped accepting new clients - press listen to start accepting new clients\n");
	}
	
	public void serverClosed() {
		status.setText("<html>Status: <font color='red'>Closed</font></html>");
		log.append("Server and all current clients are closed - press \"Listen\" to restart\n");
	}
	
	public void clientConnected(ConnectionToClient client) {
		// Displays when a client connects
		log.append("Client " + client.getId() + " connected" + "\n");
	}
	
	public void clientDisconnected(ConnectionToClient client) {
		// Displays when a client connects
		log.append("Client " + client.getId() + " disconnected" + "\n");
	}

}
