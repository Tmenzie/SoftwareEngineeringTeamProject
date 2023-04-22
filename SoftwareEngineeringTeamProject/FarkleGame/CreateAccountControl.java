package FarkleGame;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

//Author: 			Shandon Probst
//Description:		

public class CreateAccountControl implements ActionListener {
	
	private JPanel container;
	private FarkleClient client;
	private JFrame jframe;
	private GUI_Client gui;
  
	// Constructor for the create account controller.
	public CreateAccountControl(JPanel container, FarkleClient client, JFrame jframe, GUI_Client gui) {
		this.container = container;
		this.client = client;
		this.jframe = jframe;
		this.gui = gui;
	}
  
	// Handle button clicks.
	public void actionPerformed(ActionEvent ae) {
		// Get the name of the button clicked.
		String command = ae.getActionCommand();

		// Takes user back to previous screen if cancel is pressed
    	if (command == "Cancel") {
    		CardLayout cardLayout = (CardLayout)container.getLayout();
    		cardLayout.show(container, "1");
    	}

    	// Creates a new account if submit is pressed
    	else if (command == "Submit") {
    		// Fields for username, password, and verify password
    		CreateAccountPanel createAccountPanel = (CreateAccountPanel)container.getComponent(2);
    		String username = createAccountPanel.getUsername();
    		String password = createAccountPanel.getPassword();
    		String passwordVerify = createAccountPanel.getPasswordVerify();

    		// Checks if fields are entered
    		if (username.equals("") || password.equals("")) {
    			displayError("You must enter a username and password.");
    			return;
    		}
    		
    		// Checks if both passwords matched
    		else if (!password.equals(passwordVerify)) {
    			displayError("The two passwords did not match.");
    			return;
    		}
    		
    		// Checks if password is >= 6 characters
    		if (password.length() < 6) {
    			displayError("The password must be at least 6 characters.");
    			return;
    		}
      
    		// Sends new account data to server
    		CreateAccountData newAccount = new CreateAccountData(username, password);
    		try {
    			client.sendToServer(newAccount);
    		}
    		catch (IOException e) {
    			displayError("Error connecting to the server.");
    		}
    	}
	}

	// Displays contacts screen after account creation
	public void createAccountSuccess() {
		// Hide login frame
		jframe.setVisible(false);
		jframe.dispose();
		
		// Initialize game
		gui.initialize();
	}
  
	// Displays the error label
	public void displayError(String error) {
		CreateAccountPanel createAccountPanel = (CreateAccountPanel)container.getComponent(2);
		createAccountPanel.setError(error);
	}
}